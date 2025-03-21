import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Operador<T> {
    final ArrayList<String> PALABRAS_RESERVADAS = new ArrayList<>(Arrays.asList("QUOTE", "DEFUN", "SETQ", "ATOM", "LIST", "EQUAL", "<", ">", "COND"));
    private static final ArrayList<String> OPERADORES = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    private Entorno enviroment;
    private Stack<String> instrucciones = new Stack<>();
    private Stack<String> operandos = new Stack<>();
    private Predicados<String> predicados = new Predicados<>();
    private Condicionales condicionales;
    Entorno<Double> e = new Entorno<>();

    public void setEnviroment(Entorno enviroment) {
        this.enviroment = enviroment;
    }

    public void setCondicionales(Condicionales condicionales) {
        this.condicionales = condicionales;
    }

    public Entorno getEnviroment() {
        return enviroment;
    }

    public String operar(ArrayList<String> data) {
        instrucciones.addAll(data);
        while (!instrucciones.isEmpty()) {
            String value = instrucciones.pop();


            /* Devuelve expresión tal y como está */
            if (value.equals("QUOTE")) {
                return data.subList(1, data.size()).toString().replace(",", "");
            }

            if (value.startsWith("\"") && value.endsWith("\"") && value.length() >1){
                return value.substring(1, value.length() - 1);
            }


            /* Devuelve solo paréntesis y la última palabra guardada 
            if (value.equals("QUOTE")) {
                StringBuilder resultado = new StringBuilder("(");
                while (!instrucciones.isEmpty()) {
                    resultado.append(instrucciones.pop()).append(" ");
                }
                resultado.append(")");
                return resultado.toString().replace(" )",")");
            }
*/

            /* Este QUOTE funciona un 60%
            if (value.equals("QUOTE")){
                return String.join(" ", data.subList(1, data.size()));
            }
*/

            /* Devolución de cadena de texto directa */
            if (value.startsWith("\"") && value.endsWith("\"")) {
                return value.substring(1, value.length() - 1);
            }

            if (OPERADORES.contains(value)) {
                Double val1 = Double.parseDouble(operandos.pop());
                Double val2 = Double.parseDouble(operandos.pop());
                List<Double> values = new ArrayList<>(Arrays.asList(val1, val2));
                String result = String.valueOf(e.ejecutarOperacion(value, values));
                operandos.add(result);
            } else if (PALABRAS_RESERVADAS.contains(value)) {
                if (value.equals("<") || value.equals(">")) {
                    String value1 = operandos.pop();
                    String value2 = operandos.pop();
                    operandos.add(String.valueOf(predicados.MAYOR_MENOR_QUE(value, value1, value2)));
                } else {
                    switch (value) {
                        case "SETQ":
                            if (operandos.size() < 2) return "Error: SETQ necesita un identificador y un valor.";
                            String varName = operandos.pop();  // Extraer el identificador (nombre de la variable)
                            if (PALABRAS_RESERVADAS.contains(varName) || OPERADORES.contains(varName)) return "Error: No se puede usar una palabra reservada u operador como nombre de variable.";
                            String varValue = operandos.pop();  // Extraer el valor asociado
                            try {
                                Double numericValue = Double.parseDouble(varValue);
                                e.agregarVariable(varName, numericValue);  // Guardar en Entorno
                                operandos.add("NIL");  // También lo agregamos a operandos para continuidad
                            } catch (NumberFormatException ex) {
                                return "Error: El valor asignado a " + varName + " no es un número válido.";
                            }
                            break;
                        case "EQUAL":
                            operandos.add(String.valueOf(predicados.EQUAL(operandos.pop(), operandos.pop())));
                            break;
                        case "ATOM":
                            ArrayList<String> values = new ArrayList<>();
                            while (!operandos.isEmpty()) {
                                values.add(operandos.pop());
                            }
                            operandos.add(String.valueOf(predicados.ATOM(values)));
                            values.clear();
                            break;
                        case "LIST":
                            ArrayList<String> list = new ArrayList<>();
                            while (!operandos.isEmpty()) {
                                list.add(operandos.pop());
                            }
                            operandos.add(String.valueOf(predicados.LIST(list)));
                            break;
                        case "COND":
                            if (condicionales == null) {
                                return "Error: No se ha definido una función condicional.";
                            }
                            List<List<String>> condiciones = new ArrayList<>();
                            while (!instrucciones.isEmpty()) {
                                String cond = instrucciones.pop();
                                if (instrucciones.isEmpty()) break;
                                String expr = instrucciones.pop();
                                condiciones.add(Arrays.asList(cond, expr));
                            }

                            boolean conditionMet = false; // Track if any condition is met
                            
                            for (List<String> par : condiciones) {
                                ArrayList<String> tokensCond = new ArrayList<>(Arrays.asList(par.get(0)));
                                String resultadoCond = operar(tokensCond);
                                if (resultadoCond.equalsIgnoreCase("true")) {
                                    ArrayList<String> tokensExp = new ArrayList<>(Arrays.asList(par.get(1)));
                                    conditionMet = true; // Mark that a condition was met
                                    System.out.println("Evaluating condition: " + par.get(0)); // Debugging statement
                                    System.out.println();
                                    System.out.println("Executing expression: " + par.get(1)); // Debugging statement

                                    return operar(tokensExp); // Execute the corresponding expression

                                }
                            }
                            if (!conditionMet) {
                                return "NIL"; // Return "NIL" if no conditions are met
                            }
                            break;
                        default:
                            return "Valor incorrecto";
                    }
                }
            } else if (e.getFunciones().containsKey(value)) {
                //Llamado de funciones    
            } else if (e.getVariables().containsKey(value)) {
                //Llamado de variables
                String newValue = String.valueOf(e.getVariables().get(value));
                operandos.add(newValue);
            } else if (value.matches("^[+-]?\\d+$")) {
                operandos.add(value);
            } else if (value.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
                operandos.add(value);
            } else {
                return "Error el valor " + value + " no está dentro de las palabras reservadas o es el nombre de alguna de la funciones o variables existentes";
            }
        }
        if (operandos.size() != 1)
            return "Hay más elementos en la stack. Expresión incorrecta";
        return operandos.pop();
    }
}
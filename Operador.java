
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Operador<T>{
    final ArrayList<String> PALABRAS_RESERVADAS = new ArrayList<>(Arrays.asList("QUOTE", "'","DEFUN", "SETQ", "ATOM", "LIST", "EQUAL", "<", ">", "COND"));
    private static final ArrayList<String> OPERADORES = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    private Entorno enviroment;
    private Stack<String> instrucciones = new Stack<>();
    private Stack<String> operandos = new Stack<>();
    private Predicados<String> predicados = new Predicados<>();
    Entorno<Double> e = new Entorno<>();
    
    public void setEnviroment(Entorno enviroment) {
        this.enviroment = enviroment;
    }

    public Entorno getEnviroment() {
        return enviroment;
    }

    public String operar(ArrayList<String> data){
        instrucciones.addAll(data);
        while(!instrucciones.isEmpty()){
            String value = instrucciones.pop();
            if(OPERADORES.contains(value)){
                Double val1 = Double.parseDouble(operandos.pop());
                Double val2 = Double.parseDouble(operandos.pop());
                List<Double> values = new ArrayList<>(Arrays.asList(val1, val2));
                String result = String.valueOf(e.ejecutarOperacion(value, values));
                operandos.add(result);
            }
            else if(PALABRAS_RESERVADAS.contains(value)){
                if(value.equals("<") || value.equals(">")){
                    String value1 = operandos.pop();
                    String value2 = operandos.pop();
                    operandos.add(String.valueOf(predicados.MAYOR_MENOR_QUE(value, value1, value2)));
                }else{
                    switch (value) {
                        case "SETQ" :
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
                            while(!operandos.isEmpty()){
                                values.add(operandos.pop());
                            }
                            operandos.add(String.valueOf(predicados.ATOM(values)));
                            values.clear();
                        break;
                        case "LIST":
                            ArrayList<String> list = new ArrayList<>();
                            while(!operandos.isEmpty()){
                                list.add(operandos.pop());
                            }
                            operandos.add(String.valueOf(predicados.LIST(list)));
                        break;
                        case "QUOTE":
                            ArrayList<String> quotedExpression = new ArrayList<>(data.subList(1, data.size()));
                            
                            return "(" + String.join(" ", quotedExpression) + ")";
                        case "'":
                            ArrayList<String> quotedExpression2 = new ArrayList<>(data.subList(1, data.size()));
                            
                            return "(" + String.join(" ", quotedExpression2) + ")";
                        default:
                            return "Valor incorrecto";
                        
                    }
                }
            }else if (e.getFunciones().containsKey(value)) {
                //Llamado de funciones    
            }
            else if(e.getVariables().containsKey(value)){
                //Llamado de variables
                String newValue = String.valueOf(e.getVariables().get(value));
                operandos.add(newValue);

            }
            else if(value.matches("^[+-]?\\d+$")){
                operandos.add(value);
            }else if (value.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {  
                operandos.add(value);
            }
            else{
                return "Error el valor " + value + " no está dentro de las palabras reservadas o es el nombre de alguna de la funciones o variables existentes";
            }
        }
        if(operandos.size() != 1)
            return "Hay más elementos en la stack. Expresión incorrecta";
        return operandos.pop();
    }
}

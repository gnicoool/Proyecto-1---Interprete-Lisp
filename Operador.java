/**
 * Clase para operador para la evaluación de expresiones básicas de Lisp
 * 
 * Se usa una pila para la ejecución de instrucciones y otra de operandos con el objetivo de procesar
 * operaciones en notación prefix
 * 
 * @author Sergio Tan
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Operador{
    //Listado de palabras reservadas y operadores admitidos por el interprete
    final ArrayList<String> PALABRAS_RESERVADAS = new ArrayList<>(Arrays.asList("QUOTE", "DEFUN", "SETQ", "ATOM", "LIST", "EQUAL", "<", ">", "COND"));
    private static final ArrayList<String> OPERADORES = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    //Utilización de un entorno para el almacenamiento de funciones y variables
    private Entorno<Double> enviroment = new Entorno<>();
    //Pilas usadas para ejecución
    private Stack<String> instrucciones = new Stack<>();
    private Stack<String> operandos = new Stack<>();
    private Predicados<String> predicados = new Predicados<>();
    private boolean enFuncion = false; //Determina si la ejecución es en el operador principal o uno local entre funciones
    
    /**
     * Establece el entorno a usar por este operador.
     *
     * @param enviroment El entorno de ejecución (variables y funciones).
     */
    public void setEnviroment(Entorno enviroment) {
        this.enviroment = enviroment;
    }

    
    /**
     * Obtiene el entorno actual.
     *
     * @return El entorno de ejecución.
     */
    public Entorno getEnviroment() {
        return enviroment;
    }

        /**
     * Establece si se está evaluando en el contexto de una función.
     *
     * @param enFuncion Verdadero si se evalúa dentro de una función; falso en caso contrario.
     */
    public void setEnFuncion(boolean enFuncion) {
        this.enFuncion = enFuncion;
    }

    /**
     * Evalúa una expresión dada en forma de lista de tokens.
     *
     * <p>Este método procesa la lista de tokens en notación prefija,
     * diferenciando operadores, palabras reservadas y llamadas a funciones.
     *
     * @param data Lista de tokens a evaluar.
     * @return El resultado de la evaluación.
     */
    public String operar(ArrayList<String> data){
        if(data.get(0).equals("DEFUN")){ //Caso especial en el que se quiera hacer una definicion de funciones
            String nombreFuncion = data.get(1); 
            int contador = 2;
            String value = data.get(contador);
            ArrayList<String> instrucciones = new ArrayList<>();
            ArrayList<String> parametros = new ArrayList<>();
            //Recorre el arraylist hasta encontrar el primer operador, esto quiere decir que ahí empiezan las instrucciones
            //y lo anterior eran parametros
            while (!OPERADORES.contains(value)&&!PALABRAS_RESERVADAS.contains(value)&&!enviroment.getFunciones().containsKey(value)) {
                parametros.add(value);
                contador +=1;
                value = data.get(contador);
            }
            if(parametros.isEmpty())
                return "Esta función no tiene parametros";
            for(int i = contador; i < data.size(); i++){//Crear un arraylist distinto sin tomar en cuenta el defun, nombre de funcion y parametros
                instrucciones.add(data.get(i));
            }
            Funcion newFuncion = new Funcion(parametros, instrucciones);
            enviroment.getFunciones().put(nombreFuncion, newFuncion);//Crea una nueva funcion y la agrega al map para su uso
            return "NIL";
        }
        instrucciones.addAll(data);
        while(!instrucciones.isEmpty()){ //Mientras la pila de instrucciones no esté vacía sacará un valor y evaluará que hacer con el
            String value = instrucciones.pop();
            if(OPERADORES.contains(value)){ //Si es un operador saca dos valores de la pila de operandos y llama al map de operaciones aritmeticas para ejecutar la función
                Double val1 = Double.parseDouble(operandos.pop());
                Double val2 = Double.parseDouble(operandos.pop());
                List<Double> values = new ArrayList<>(Arrays.asList(val1, val2));
                String result = String.valueOf(enviroment.ejecutarOperacion(value, values));
                operandos.add(result); //El resultado es agregado a la pila de operandos para su uso
            }
            else if(PALABRAS_RESERVADAS.contains(value)){ //En caso de ser una palabra reservada
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
                                enviroment.agregarVariable(varName, numericValue);  // Guardar en Entorno
                                if (!enFuncion) {
                                    operandos.add("NIL");
                                }
                            } catch (NumberFormatException ex) {
                                return "Error: El valor asignado a " + varName + " no es un número válido.";
                            }
                        break;
                        case "EQUAL":
                            operandos.add(String.valueOf(predicados.EQUAL(operandos.pop(), operandos.pop())));
                        break;
                        case "ATOM":
                            ArrayList<String> values = new ArrayList<>();
                            while(!operandos.isEmpty()){ //Obtiene todos los valores de operandos para usarlos en la funcion ATOM
                                values.add(operandos.pop());
                            }
                            operandos.add(String.valueOf(predicados.ATOM(values)));
                            values.clear();
                        break;
                        case "LIST":
                            ArrayList<String> list = new ArrayList<>(); 
                            while(!operandos.isEmpty()){ //Obtiene todos los operandos para evaluarlos en LIST
                                list.add(operandos.pop());
                            }
                            operandos.add(String.valueOf(predicados.LIST(list)));
                        break;
                        default:
                            return "Valor incorrecto";
                    }
                }
            }else if (enviroment.getFunciones().containsKey(value)) { //Si el valor a evaluar está dentro de las funciones almacenadas
                //Crea un entorno local para evitar conflicos con variables, pero copia las funciones almacenadas
                Entorno<Double> entornoLocal = new Entorno<>();
                entornoLocal.setFunciones(enviroment.getFunciones());  
                Funcion f = enviroment.getFunciones().get(value); //Obtiene el objeto funcion del map en base a la palabra reservada
                ArrayList<String> valores = new ArrayList<>();
                if(operandos.size() < f.getNumParametros())
                    return "No hay suficientes valores en la stack para pasarlos de parametros";
                for(int i=0; i<f.getNumParametros();i++){
                    valores.add(operandos.pop());
                }
                operandos.push(f.evaluarFuncion(valores, entornoLocal)); //Se agrega a la pila de operandos el resultado de la evaluación de la función
            }
            else if(enviroment.getVariables().containsKey(value)){
                //Llamado de variables, intercambia el nombre de la variable por su valor dentro del map
                String newValue = String.valueOf(enviroment.getVariables().get(value));
                operandos.add(newValue);
            }
            else if(value.matches("^[+-]?\\d+(\\.\\d+)?$")){ //Si es un número entero o decimal lo agrega a operandos
                operandos.add(value);
            }else if (value.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {  //Si es una letra o palabra (Usados para variables o funciones) lo agrega a operandos
                operandos.add(value);
            }
            else{
                return "Error el valor " + value + " no está dentro de las palabras reservadas o es el nombre de alguna de la funciones o variables existentes";
            }
        }
        if(operandos.size() != 1) //Al final evalua si hay más de un elemento en la pila de operandos, si es correcto es porque la expresión no estba bien definida en el txt.
                                // Caso contrario devielve el valor restante de la pila operandos
            return "Hay más elementos en la stack. Expresión incorrecta";
        return operandos.pop();
    }
}

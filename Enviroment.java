
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Enviroment<T>{
    private HashMap<String, T> variables;
    private HashMap<String, List<String>> funciones;
    private final HashMap<String, Function<List<T>, T>> operacionesAritmeticas; //Al ser operaciones directas se usa una estructura donde se pasan una lista de parametros y se devuelve un valor

    public Enviroment() {
        variables = new HashMap<>();
        funciones = new HashMap<>();
        operacionesAritmeticas = new HashMap<>();
        agregarOpAritmeticas();
    }

    //Getters y Setters
    public HashMap<String, List<String>> getFunciones() {return funciones;}
    public HashMap<String, T> getVariables() {return variables;}
    public HashMap<String, Function<List<T>, T>> getOperacionesAritmeticas() {return operacionesAritmeticas;}
    public void setFunciones(HashMap<String, List<String>> funciones) {this.funciones = funciones;}
    public void setVariables(HashMap<String, T> variables) {this.variables = variables;}

    //Operaciones aritmeticas
    @SuppressWarnings("unchecked")
    private void agregarOpAritmeticas(){
        //Recibe una lista de datos genericos que luego es convertida a tipo de dato double para ser operada posteriormente, de igual manera el orElse devuelve un valor predeterminado en caso de error en la operación
        operacionesAritmeticas.put("+", args -> (T) (Double) args.stream().mapToDouble(x -> ((Number) x).doubleValue()).reduce((a,b)-> a+b).orElse(0));
        operacionesAritmeticas.put("-", args -> (T) (Double) args.stream().mapToDouble(x -> ((Number) x).doubleValue()).reduce((a,b)-> a-b).orElse(0));
        operacionesAritmeticas.put("*", args -> (T) (Double) args.stream().mapToDouble(x -> ((Number) x).doubleValue()).reduce((a,b)-> a*b).orElse(1));
        operacionesAritmeticas.put("/", args -> (T) (Double) args.stream().mapToDouble(x -> ((Number) x).doubleValue()).reduce((a,b)-> a/b).orElse(1));
    }

    //Ejecución de las operaciones aritmeticas y predicados
    public T ejecutarOperacion(String operationName, List<T> values){
        if(operacionesAritmeticas.containsKey(operationName))
            return operacionesAritmeticas.get(operationName).apply(values);
        throw new RuntimeException("Operación no encontrada: "+ operationName);
    }

    //Predicados
        //Mayor y menor que
    public boolean MAYOR_MENOR_QUE(String predicado, T val1, T val2){
        double value1 = 0.00;
        double value2 = 0.00;
        if(val1 instanceof Number && val2 instanceof Number){//Revisa si los valores son números
            value1 = ((Number) val1).doubleValue();
            value2 = ((Number) val2).doubleValue();
        }else
            throw new NumberFormatException("La función mayor que solo admite valores numericos");
        switch (predicado) { //Dependiendo el predicado ingresado da una respuesta distinta
            case ">" -> {return value1 > value2;}
            case "<" -> {return value1 < value2;}
            default -> throw new RuntimeException("El predicado ingresado no es mayor o menor que");
        }
    }

        //

    //SETQ
    public void agregarVariable(String name, T valor){
        if(name == null|| name.isEmpty())
         throw new IllegalArgumentException("Se debe ingresar un nombre para asociar al valor");
        else if(operacionesAritmeticas.containsKey(name))
            throw new RuntimeException("Este nombre pertenece a una operación predeterminada");
        variables.put(name, valor);
    }

}
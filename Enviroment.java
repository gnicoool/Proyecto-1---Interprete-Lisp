
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Enviroment<T>{
    private HashMap<String, T> variables;
    private HashMap<String, List<String>> funciones;
    private HashMap<String, Function<List<T>, T>> operacionesAritmeticas; //Al ser operaciones directas se usa una estructura donde se pasan una lista de parametros y se devuelve un valor

    public Enviroment() {
    }

    //Getters y Setters
    public HashMap<String, List<String>> getFunciones() {
        return funciones;
    }
    public HashMap<String, T> getVariables() {
        return variables;
    }
    public HashMap<String, Function<List<T>, T>> getOperacionesAritmeticas() {
        return operacionesAritmeticas;
    }

    public void setFunciones(HashMap<String, List<String>> funciones) {
        this.funciones = funciones;
    }
    public void setVariables(HashMap<String, T> variables) {
        this.variables = variables;
    }

    //Operaciones aritmeticas
    @SuppressWarnings("unchecked")
    public void agregarOpAritmeticas(){
        //Recibe una lista de datos genericos que luego es convertida a tipo de dato double para ser operada posteriormente, de igual manera el orElse devuelve un valor predeterminado en caso de error en la operación
        operacionesAritmeticas.put("+", args -> (T) (Double) args.stream().mapToDouble(x -> (Double) x).reduce((a,b)-> a+b).orElse(0));
        operacionesAritmeticas.put("-", args -> (T) (Double) args.stream().mapToDouble(x -> (Double) x).reduce((a,b)-> a-b).orElse(0));
        operacionesAritmeticas.put("*", args -> (T) (Double) args.stream().mapToDouble(x -> (Double) x).reduce((a,b)-> a*b).orElse(1));
        operacionesAritmeticas.put("/", args -> (T) (Double) args.stream().mapToDouble(x -> (Double) x).reduce((a,b)-> a/b).orElse(1));
    }

    //SETQ
    public void agregarVariable(String name, T valor){
        variables.put(name, valor);
    }

}
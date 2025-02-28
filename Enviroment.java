
import java.util.HashMap;
import java.util.function.Function;

public class Enviroment{
    private HashMap<String, Object> variables;
    private HashMap<String, Function> funciones;

    public HashMap<String, Function> getFunciones() {
        return funciones;
    }
    public HashMap<String, Object> getVariables() {
        return variables;
    }

    public void setFunciones(HashMap<String, Function> funciones) {
        this.funciones = funciones;
    }
    public void setVariables(HashMap<String, Object> variables) {
        this.variables = variables;
    }
}
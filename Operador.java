
import java.util.List;

public class Operador {
    private Entorno enviroment;

    public Object evaluate(Object object, Entorno env){
        return object;
    };

    public Object applyFunction(String func, List<Object> obL){
        Object ob = new Object();
        return ob;
    }
    
    public void setEnviroment(Entorno enviroment) {
        this.enviroment = enviroment;
    }

    public Entorno getEnviroment() {
        return enviroment;
    }
}


import java.util.List;

public class Evaluator {
    private Enviroment enviroment;

    public Object evaluate(Object object, Enviroment env){
        return object;
    };

    public Object applyFunction(String func, List<Object> obL){
        Object ob = new Object();
        return ob;
    }
    
    public void setEnviroment(Enviroment enviroment) {
        this.enviroment = enviroment;
    }

    public Enviroment getEnviroment() {
        return enviroment;
    }
}

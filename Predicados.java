
import java.util.List;

public class Predicados<T>{
    //Predicados
    //Mayor y menor que
    public boolean MAYOR_MENOR_QUE(String predicado, T val1, T val2){
        double value1 = 0.00;
        double value2 = 0.00;
        try {
            value1 = Double.parseDouble(String.valueOf(val1));
            value2 = Double.parseDouble(String.valueOf(val2));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("La función mayor que solo admite valores numericos");
        }
        switch (predicado) { //Dependiendo el predicado ingresado da una respuesta distinta
            case ">" -> {return value1 > value2;}
            case "<" -> {return value1 < value2;}
            default -> throw new RuntimeException("El predicado ingresado no es mayor o menor que");
        }
    }

    //Convierte los datos a un String para compararlos con un EQUALS
    public boolean EQUAL(T val1, T val2){
        String val1Str = val1.toString();
        String val2Str = val2.toString();
        return val1Str.equals(val2Str);
    }

    public boolean ATOM(List<T> data){
        return data.size()==1;
    }

    public boolean LIST(List<T> data){
        return data.size()!=1 && !data.isEmpty();
    }
}

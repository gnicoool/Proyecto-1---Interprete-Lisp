/**
 * Clase predicados para la evaluacion de condicionales simples de lisp
 * 
 * Acepta valores genericos, pero que sean comparables, como numeros
 * 
 * @author Sergio Tan
 */
import java.util.List;

public class Predicados<T>{
    /**
     * Recibe 2 valores y en base a predicado elegido realizará la condición
     * 
     * @param predicado Caso a evaluar, puede ser < o >
     * @param val1 Primer valor a evaluar
     * @param val2 Segundo valor a evaluar
     * @return valor verdadero o falso en función a la condición
     * @throws NumberFormatException en caso de que los valores recibidos no sean numericos
     * @throws RuntimeException en caso de que predicado no exista
     */
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

    /**
     * Evalua si dos expresiones son iguales al convertirlas a cadenas de texto y hacer un equals
     * 
     * @param val1 Primer valor a evaluar
     * @param val2 Segundo valor a evaluar
     * @return Verdadero si son iguales, falso en caso contrario
     */
    public boolean EQUAL(T val1, T val2){
        String val1Str = val1.toString();
        String val2Str = val2.toString();
        return val1Str.equals(val2Str);
    }

    /**
     * Evalua si lo recibido es un dato unico o un listado de ellos
     * 
     * @param data listado de datos en los que se basa para la evaluación
     * @return verdadero si el tamaño del listado es 1; falso en caso contrario
     */
    public boolean ATOM(List<T> data){
        return data.size()==1;
    }

    /**
     * Evalua si lo recibido es un listado de valores
     * 
     * @param data listado de datos en los que se basa para la evaluación
     * @return verdadero si el tamaño del listado es distinto a 1 y 0; falso en caso contrario
     */
    public boolean LIST(List<T> data){
        return data.size()!=1 && !data.isEmpty();
    }
}

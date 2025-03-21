/**
 * Clase para manejar las expresiones COND.
 * 
 * Esta clase se utiliza para evaluar condiciones y expresiones en el contexto de la lógica de programación.
 * Se usa una lista para la ejecución de condiciones y otra de expresiones con el objetivo de procesar
 * la logística de los COND.
 * 
 * @author Alejandra Avilés
 */

import java.util.List;

public class Condicionales {
    private Operador operador;
    private Lector lector; 

    /**
     * Constructor de la clase Condicionales.
     * 
     * @param operador El operador que se utilizará para evaluar las expresiones.
     * @param lector El lector que se utilizará para procesar las entradas.
     */
    public Condicionales(Operador operador, Lector lector) { 

        this.operador = operador;
        this.lector = lector;
    }

    /**
     * Evalúa una lista de condiciones y devuelve el resultado de la primera condición verdadera.
     * 
     * @param condiciones Una lista de listas, donde cada sublista contiene una condición y una expresión.
     * @return El resultado de la expresión correspondiente a la primera condición verdadera, o un mensaje de error si no se cumple ninguna.
     */
    public String evaluarCond(List<List<String>> condiciones) { 

        for (List<String> parCondExp : condiciones) {
            if (parCondExp.size() < 2) {
                return "Error: La condición debe tener un operador y un valor (Formato incorrecto en COND).";
            }

            //List <String> tokensCond = new ArrayList<>(parCondExp.subList(0, parCondExp.size() - 1));
            //String resultadoCond = operador.operar(new ArrayList<>(tokensCond));
            //System.out.println(resultadoCond);

            String resultadoCond = operador.operar(lector.tokenRegex(parCondExp.get(0)));
            boolean esVerdadero = Boolean.parseBoolean(resultadoCond);

            if (esVerdadero) {
                return operador.operar(lector.tokenRegex(parCondExp.get(1)));
            }
            /*
            if (resultadoCond.equalsIgnoreCase("true")) {
                //List<String> tokensExp = new ArrayList<>(List.of(parCondExp.get(parCondExp.size() - 1)));
                //return operador.operar(new ArrayList<>(tokensExp));
                System.out.println(operador.operar(lector.tokenRegex(parCondExp.get(1))));
                break;*/
            }

        /*
        while (!condiciones.isEmpty()) {
            String condicion = condiciones.pop();
            String expresion = condiciones.pop();

            ArrayList<String> tokensCond = lector.tokenRegex(condicion);
            String resultadoCond = operador.operar(tokensCond);

            if (resultadoCond.equalsIgnoreCase("true")) {
                ArrayList<String> tokensExp = lector.tokenRegex(condicion);
                return operador.operar(tokensExp);
            }*/
            /*
            if (evaluar(condicion)) {
                ArrayList<String> tokens = lector.tokenRegex(expresion); // Use tokenRegex from Lector
                return operador.operar(tokens); // Evaluate the expression
            }*/
        
        return "NIL"; // Return NIL if no conditions are met
    }

    /**
     * Evalúa si una condición dada es verdadera.
     * 
     * @param condicion La condición a evaluar.
     * @return true si la condición es verdadera, false en caso contrario.
     */
    private boolean evaluar(String condicion) { 

        try {
            return Boolean.parseBoolean(condicion);   
        } catch (Exception e) {
            return false;
        }
    }
}

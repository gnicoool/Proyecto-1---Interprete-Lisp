/**
 * Clase para manejar las expresiones COND
 * 
 * Se usa una lista para la ejecución de condiciones y otra de expresiones con el objetivo de procesar
 * la logística de los COND
 * 
 * @author Alejandra Avilés
 */

import java.util.List;

public class Condicionales {
    private Operador<Double> operador;
    private Lector lector; 

    public Condicionales(Operador<Double> operador, Lector lector) {
        this.operador = operador;
        this.lector = lector;
    }

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

    private boolean evaluar(String condicion) {
        try {
            return Boolean.parseBoolean(condicion);   
        } catch (Exception e) {
            return false;
        }
    }
}

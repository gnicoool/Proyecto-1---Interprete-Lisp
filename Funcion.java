/**
 * Clase para la estructura de una función de lisp
 * 
 * Utiliza un listado de parametros e instrucciones para hacer dinamicas la cantidad que pueden haber por función.
 * 
 * @author Sergio Tan
 */
import java.util.ArrayList;

public class Funcion {
    private ArrayList<String> parametros = new ArrayList<>();
    private ArrayList<String> instrucciones = new ArrayList<>();
    private int numParametros = 0;

    public Funcion(ArrayList<String> parametros, ArrayList<String> instrucciones) {
        this.parametros = parametros;
        this.instrucciones = instrucciones;
        this.numParametros = parametros.size();
    }

    /** Evalua una serie de instrucciones asociadas a una key en el map de funciones
     * 
     * @param Arraylist con los valores a evaluar dentro de la función. Un entorno local para evitar conflictos con variables del entorno base
     * @return String con el resultado de la función operar
     * @throws IllegalArgumentException cuando no los valores obtenidos no son los mismos que la cantidad de parametros
     */
    public String evaluarFuncion(ArrayList<String> valores, Entorno<Double> entornoLocal){
        if(valores.size() != getNumParametros())
        throw new IllegalArgumentException("No hay la misma cantidad de valores que cantidad de parámetros");
        // Crear un Operador local para evaluar el cuerpo de la función
        Operador operadorLocal = new Operador();
        operadorLocal.setEnviroment(entornoLocal);  
        operadorLocal.setEnFuncion(true); //Establece enFuncion como true para no hacer push innecesarios en stack de operandos
        
        // Asignar los parámetros en el entorno local
        for (int i = 0; i < getParametros().size(); i++) {
            try {
                ArrayList<String> initValues = new ArrayList<>();
                //Si los valores no están inicializados prepara una instrucción para operar
                if(!entornoLocal.getVariables().containsKey(getParametros().get(i))){
                    initValues.add("SETQ");
                    initValues.add(getParametros().get(i));
                    initValues.add(valores.get(i));
                }
                if(!initValues.isEmpty()){//Si existe una instruccion llama a operar para agregarla al map de variables
                    operadorLocal.operar(initValues);
                }
            } catch (NumberFormatException ex) {
                return "Error al asignar el parámetro " + getParametros().get(i) + ": " + ex.getMessage();
            }
        }
        //
        String resultado = operadorLocal.operar(getInstrucciones()); //Ejecuta las instrucciones de la función en un operador local
        operadorLocal.setEnFuncion(false); //Regresa enFuncion a su estado base
        return resultado;
    }

    /**
     * Obtiene el numero de parametros necesarios para la función
     * 
     * @return int con el Número de parametros
     */
    public int getNumParametros() {
        return numParametros;
    }

    /*
     * Obtiene el listado de parametros de la función
     * 
     * @return Listado de parametros
     */
    public ArrayList<String> getParametros() {
        return parametros;
    }
    
    /*
     * Obtiene un listado de instrucciones
     * 
     * @return Listado de instrucciones asociadas a una función
     */
    public ArrayList<String> getInstrucciones() {
        return instrucciones;
    }
}

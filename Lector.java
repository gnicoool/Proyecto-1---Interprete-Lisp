import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lector {
    final ArrayList<String> PALABRAS_RESERVADAS = new ArrayList<>(Arrays.asList("QUOTE", "DEFUN", "SETQ", "ATOM", "LIST", "EQUAL", "<", ">", "COND"));
    private static final ArrayList<String> OPERADORES = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    private final ArrayList<String> nombresFunciones = new ArrayList<>();

    /*Lee archivo txt y devuelve el contenido como un String
     * @return String con el contenido del txt
     * @throws IOException si no se encuentra el archivo o no se puede leer
     */
    public String lector(){
        StringBuilder leer = new StringBuilder();
        try {
            FileReader fr = new FileReader("Datos.txt");
            BufferedReader lector = new BufferedReader(fr);
            String linea = lector.readLine();/*lee linea a linea el txt */
            while (linea!= null) {
                leer.append(linea);/*Agrega linea actual al StringBuilder */
                leer.append("\n");  
                linea = lector.readLine(); /*Lee la siguiente linea */
            }
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leer.toString();/*Devuelve el contenido de txt como un string */
    }

    /*Separa las funciones de un String segun parentesis balanceados y las almacena en un ArrayList
     * @param linea String con el contenido del txt
     * @return ArrayList con las funciones encontradas
     */
    public ArrayList<String> sintaxis(String linea){
        Stack<String> pila = new Stack<>();
        ArrayList<String> fun = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (char c : linea.toCharArray()) {
            if (c == '(') { /*Si encuentra un parentesis abierto, lo agrega a la pila */
                pila.push("(");
                if (pila.size() == 1) {
                    sb.setLength(0); // Reiniciar el buffer cuando empieza una nueva expresión
                }
                sb.append(c);
            } else if (c == ')') {
                if (!pila.isEmpty()) {/*Si encuentra un parentesis cerrado saca la funcion de la pila */
                    pila.pop();
                    sb.append(c);
                    
                    if (pila.isEmpty()) {/*Si la pila esta vacia, agrega la funcion al ArrayList */
                        fun.add(sb.toString().trim()); // Agregar la función completa
                    }
                }
            }else{
                if (!pila.isEmpty()) {
                    sb.append(c);
                    
                }
            }
        }
        return fun;/*Devuelve un ArrayList con las funciones */
    }

    /*Separa los tokens de una funcion usando expresiones regulares y los almacena en un ArrayList
     *Tokens posibles: cadenas, numeros, operadores y parentesis
     * @param funciones String con la funcion encontradas em el txt
     * @return ArrayList con los tokens encontrados
     */
    public ArrayList<String> tokenRegex(String funciones){
        ArrayList<String> tokens = new ArrayList<>();
        String patron = "\"[^\"]*\"|[0-9]+\\.?[0-9]*|[a-zA-Z\\+\\-\\*\\/\\=\\_\\?\\!\\@\\#\\$\\%\\^\\&][a-zA-Z0-9\\+\\-\\*\\/\\=\\_\\?\\!\\@\\#\\$\\%\\^\\&]*|\\(|\\)";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(funciones);

        while (matcher.find()) {
            String token = matcher.group().trim();
            if (!token.isEmpty()&& !token.equals("(") && !token.equals(")")) {/*Si el token no esta vacio y no es un parentesis, lo agrega al ArrayList */
                tokens.add(token);   
            }
        }
        return tokens;/*Devuelve un ArrayList con los tokens */
    }


    /*Procesa el archivo txt y devuelve un ArrayList de ArrayList con los tokens de cada funcion
     * lee el archivo, separa las funciones y los tokens de cada funcion
     * @return ArrayList de ArrayList con los tokens de cada funcion
     */
    public ArrayList<ArrayList<String>> procesarArchivo(){
        String texto = lector();
        ArrayList<String> funciones = sintaxis(texto);
        ArrayList<ArrayList<String>> alltokens = new ArrayList<>();

        for (String funcion : funciones) {
            ArrayList<String> tokens = tokenRegex(funcion);
            if(esPrefix(tokens))//Si la operación es prefix la agrega a la lista
                alltokens.add(tokens);
            else 
                System.err.println("Error: Función inválida detectada - " + funcion);
        }
        return alltokens;/*Devuelve un ArrayList de ArrayList con los tokens de cada funcion */
    }


    public boolean esPrefix(ArrayList<String> datos){
        if (datos.isEmpty()) {
            return false;
        }
        String primerToken = datos.get(0);
        if(primerToken.equals("DEFUN"))
            nombresFunciones.add(datos.get(1));
        return PALABRAS_RESERVADAS.contains(primerToken) || OPERADORES.contains(primerToken)||nombresFunciones.contains(primerToken);
    }
}

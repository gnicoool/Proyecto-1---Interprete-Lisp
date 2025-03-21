import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        Lector l = new Lector();
        ArrayList<ArrayList<String>> funcionesValidas = l.procesarArchivo();
        Operador o = new Operador();
        // Imprimir los tokens de las funciones válidas
        for (ArrayList<String> tokens : funcionesValidas) {
            System.out.println(o.operar(tokens));
            System.out.println();
        }}
}
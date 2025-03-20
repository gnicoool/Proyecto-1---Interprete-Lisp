import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        Lector l = new Lector();
        ArrayList<ArrayList<String>> funcionesValidas = l.procesarArchivo();
        Operador<String> o = new Operador<>();
        // Imprimir los tokens de las funciones válidas
        /* for (ArrayList<String> tokens : funcionesValidas) {
            for (String token : tokens) {
                System.out.println("  " + token);
            }
            System.out.println();
        } */
        System.out.println(o.operar(funcionesValidas.get(0)));
        System.out.println(o.operar(funcionesValidas.get(1)));
        System.out.println(o.operar(funcionesValidas.get(2)));
        System.out.println(o.operar(funcionesValidas.get(3)));
        System.out.println(o.operar(funcionesValidas.get(4)));
    }
}
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        Lector l = new Lector();
        ArrayList<ArrayList<String>> funcionesValidas = l.procesarArchivo();
        Operador<String> o = new Operador<>();

        //System.out.println(Condicionales);
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
        /*System.out.println(o.operar(funcionesValidas.get(4)));
        System.out.println(o.operar(funcionesValidas.get(5)));
        System.out.println(o.operar(funcionesValidas.get(6)));
        System.out.println(o.operar(funcionesValidas.get(7)));
        System.out.println(o.operar(funcionesValidas.get(8)));
        System.out.println(o.operar(funcionesValidas.get(9)));
        System.out.println(o.operar(funcionesValidas.get(10)));
        System.out.println(o.operar(funcionesValidas.get(11)));
        System.out.println(o.operar(funcionesValidas.get(12)));
        System.out.println(o.operar(funcionesValidas.get(13)));
        System.out.println(o.operar(funcionesValidas.get(14)));*/
    }
}
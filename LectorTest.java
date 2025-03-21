import org.junit.Test; 
import static org.junit.Assert.*; 
import java.util.ArrayList; 

public class LectorTest {
    Lector l = new Lector();

    @Test
    public void testLector(){
        String contenido = l.lector();
        assertNotNull(contenido);
        assertTrue(contenido.contains("(+ 15 (+ 2 3))"));
    }

    @Test
    public void testSintaxis(){
        String text = "(EQUAL 1 2) (> 12 5) (+ 15 (+ 2 3))";
        ArrayList<String> funciones = l.sintaxis(text);
        assertEquals("(EQUAL 1 2)", funciones.get(0));
        assertEquals("(> 12 5)", funciones.get(1));
        assertEquals("(+ 15 (+ 2 3))", funciones.get(2));
    }

    @Test
    public void testTokenRegex(){
        ArrayList<String> tokens = l.tokenRegex("(+ 15 (+ 2 3))");
        assertEquals(5, tokens.size());
        assertEquals("+", tokens.get(0));
        assertEquals("15", tokens.get(1));
        assertEquals("+", tokens.get(2));
        assertEquals("2", tokens.get(3));
        assertEquals("3", tokens.get(4));
    }

    @Test
    public void testSintaxisCond() {
        String text = "(COND (> 1 2) (EQUAL 1 1))";
        ArrayList<String> funciones = l.sintaxis(text);
        assertEquals(1, funciones.size());
        assertTrue(funciones.get(0).contains("COND"));
    }

    @Test
    public void testTokenRegexCond() {
        ArrayList<String> tokens = l.tokenRegex("(COND (> 1 0) (+ 1 2))");
        assertEquals(7, tokens.size());

        assertEquals("COND", tokens.get(0));
        assertEquals(">", tokens.get(1));
        assertEquals("1", tokens.get(2));
        assertEquals("0", tokens.get(3));
        assertEquals("+", tokens.get(4));
        assertEquals("1", tokens.get(5));
        assertEquals("2", tokens.get(6));
    }
}

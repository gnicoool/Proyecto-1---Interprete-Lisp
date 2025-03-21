import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

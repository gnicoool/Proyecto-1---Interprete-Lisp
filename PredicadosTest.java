import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class PredicadosTest {

    Predicados<Double> predicados = new Predicados<>();

    @Test
    public void mayorMenorQueTest(){
        assertTrue(predicados.MAYOR_MENOR_QUE(">", 25.0, 3.0));
        assertTrue(predicados.MAYOR_MENOR_QUE("<", 3.0, 25.0));
        assertFalse(predicados.MAYOR_MENOR_QUE(">", 3.0, 25.0));
        assertFalse(predicados.MAYOR_MENOR_QUE("<", 25.0, 3.0));
    }

    @Test
    public void equalTest(){
        assertTrue(predicados.EQUAL(3.0, 3.0));
        assertFalse(predicados.EQUAL(3.0, 10.0));

    }
}
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EntornoTest {
    Entorno<Double> entorno = new Entorno<>();
    List<Double> valores = new ArrayList<>();
     
    @Test 
    public void sumaTest(){
        valores.add(8.0);
        valores.add(2.0);
        assertEquals(10.0, entorno.ejecutarOperacion("+", valores), 0.001);
    }

    @Test 
    public void restaTest(){
        valores.add(8.0);
        valores.add(2.0);
        assertEquals(6.0, entorno.ejecutarOperacion("-", valores), 0.001);
    }

    @Test 
    public void multiplicacionTest(){
        valores.add(8.0);
        valores.add(2.0);
        assertEquals(16.0, entorno.ejecutarOperacion("*", valores), 0.001);
    }

    @Test 
    public void divisionTest(){
        valores.add(8.0);
        valores.add(2.0);
        assertEquals(4.0, entorno.ejecutarOperacion("/", valores), 0.001);
    }

    @Test
    public void agregarVariableTest(){
        entorno.agregarVariable("x", 25.0);
        assertEquals(25.0, entorno.getVariables().get("x"), 0.001);
    }

}

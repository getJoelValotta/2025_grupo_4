package modeloDatosTest;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.*;

public class AutoTest{


    @Test
    public void testAuto1() {
        Auto a = new Auto("AAAA", 2, false);
        assertNotNull(a);
    }


    @Test
    public void testGetPuntajePedido1() {      //Testeo con baul
        Auto a = new Auto("AAAA", 4, false);
        Cliente c = new Cliente("JJuan", "AA", "Juan");
        Pedido p = new Pedido(c, 3, false, true, 20, "ZONA_PELIGROSA");
        int esperado = 120; // puntaje = 40 * 3
        int actual = a.getPuntajePedido(p);
        assertEquals(esperado,actual);
    }

    @Test
    public void testGetPuntajePedido2() {      //Testeo sin baul
        Auto a = new Auto("AAAA", 4, false);
        Cliente c = new Cliente("JJuan", "AA", "Juan");
        Pedido p = new Pedido(c, 3, false, false, 20, "ZONA_PELIGROSA");
        int esperado = 90; // puntaje = 30 * 3
        int actual = a.getPuntajePedido(p);
        assertEquals(esperado,actual);
    }

    @Test
    public void testGetPuntajePedido3() {      //Testeo sin baul
        Auto a = new Auto("AAAA", 3, false);
        Cliente c = new Cliente("JJuan", "AA", "Juan");
        Pedido p = new Pedido(c, 4, false, false, 20, "ZONA_PELIGROSA");
        assertNull(a.getPuntajePedido(p));
    }

    @Test
    public void testGetPuntajePedido4() {      //Testeo sin baul
        Auto a = new Auto("AAAA", 3, false);
        Cliente c = new Cliente("JJuan", "AA", "Juan");
        Pedido p = new Pedido(c, 3, true, false, 20, "ZONA_PELIGROSA");
        assertNull(a.getPuntajePedido(p));
    }

    @Test
    public void testGetPatente() {
        Auto a = new Auto("AAAA", 3, false);
        String esperado = "AAAA";
        String actual = a.getPatente();
        assertEquals(esperado, actual);
    }

    @Test
    public void testGetCantidadPlazas() {
        Auto a = new Auto("AAAA", 3, false);
        int esperado = 3;
        int actual = a.getCantidadPlazas();
        assertEquals(esperado, actual);
    }

    @Test
    public void testIsMascota() {
        Auto a = new Auto("AAAA", 3, true);
        boolean esperado = true;
        boolean actual = a.isMascota();
        assertEquals(esperado, actual);
    }

}
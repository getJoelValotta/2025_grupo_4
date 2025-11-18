package modeloDatosTest;

import modeloDatos.*;
import org.junit.*;
import util.Constantes;

import static org.junit.Assert.*;

public class CombiTest {
    //Escenario unico

    @Test
    public void testConstructorLimiteA(){
        Combi combi = new Combi("AAA",5, false);
        assertEquals("AAA",combi.getPatente());
        assertEquals("Falla en limite inferior CantidadPlazas",5,combi.getCantidadPlazas());
        assertFalse("Falla en isMascota",combi.isMascota());
    }

    public void testConstructorLimiteB(){
        Combi combi = new Combi("AAA",10, false);
        assertEquals("AAA",combi.getPatente());
        assertEquals("Falla en limite superior CantidadPlazas",10,combi.getCantidadPlazas());
        assertFalse("Falla en isMascota",combi.isMascota());
    }

    @Test
    //ESC A:  no se solicita baul en el pedido.
    public void testPuntajePedidoESCA(){
        Combi combi = new Combi("AAA",5, false);
        Pedido pedido = new Pedido(new Cliente("Pedro","123","Pedro Pascal"),5, false, false, 10, Constantes.ZONA_PELIGROSA);
        assertEquals(new Integer(10*5),combi.getPuntajePedido(pedido));
    }

    //ESC B:  se solicita baul en el pedido.
    public void testPuntajePedidoESCB(){
        Combi combi = new Combi("AAA",5, false);
        Pedido pedido = new Pedido(new Cliente("Pedro","123","Pedro Pascal"),5, false, true, 10, Constantes.ZONA_PELIGROSA);
        assertEquals(new Integer(10*5 + 100),combi.getPuntajePedido(pedido));
    }

    //ESC C:  no se solicita baul en el pedido.
    @Test
    public void testPuntajePedidoESCC(){
        Combi combi = new Combi("AAA",5, false);
        Pedido pedido = new Pedido(new Cliente("Pedro","123","Pedro Pascal"),40, false, false, 10, Constantes.ZONA_PELIGROSA);
        assertNull(combi.getPuntajePedido(pedido));
    }


}

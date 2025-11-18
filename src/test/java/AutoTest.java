import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.*;

public class AutoTest{

	@Test
	public void testAuto() {
		Auto a = new Auto("AAAA", 4, true);
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

}

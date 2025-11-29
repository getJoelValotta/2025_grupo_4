package modeloDatosTest;

import static org.junit.Assert.*;

import org.junit.Test;
import modeloDatos.*;
public class PedidoTest {
	Cliente cli = new Cliente("Jor", "123jorge", "Jorge");
	Pedido p = new Pedido(cli, 2, true, false, 20, "ZONA_STANDARD");

	@Test
	public void testPedido() {
		assertNotNull(p);
	}
	
	@Test
	public void testGetCliente() {
		Cliente esperado = cli;
		Cliente actual = p.getCliente();
		assertSame(esperado, actual);
	}

	@Test
	public void testIsMascota() {
		boolean esperado = true;
		boolean actual = p.isMascota();
		assertEquals(esperado,actual);
	}
	
	@Test
	public void testIsBaul() {
		boolean esperado = false;
		boolean actual = p.isBaul();
		assertEquals(esperado,actual);
	}
	
	@Test
	public void testGetKm() {
		int esperado = 20;
		int actual = p.getKm();
		assertEquals(esperado,actual);
	}
	
	@Test
	public void testGetZona() {
		String esperado = "ZONA_STANDARD";
		String actual = p.getZona();
		assertEquals(esperado,actual);
	}
	
	@Test
	public void testCantidadPasajeros() {
		int esperado = 2;
		int actual = p.getCantidadPasajeros();
		assertEquals(esperado,actual);
	}
}

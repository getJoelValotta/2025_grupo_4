package modeloDatosTest;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.*;


public class MotoTest {
	Moto m = new Moto("AAA");

	@Test
	public void testMoto() {
		assertNotNull(m);
		int esperado = 1;
		int actual = m.getCantidadPlazas();
		assertEquals(esperado,actual);
		boolean mascotaEsperado = false;
		boolean mascotaActual = m.isMascota();
		assertEquals(mascotaEsperado,mascotaActual);
	}

	
	@Test
	public void testGetPuntajePedido1() {
		Cliente cli = new Cliente("Jor", "123jorge", "Jorge");
		Pedido p = new Pedido(cli, 1, false, false, 20, "ZONA_STANDARD");
		int esperado = 1000;
		int actual = m.getPuntajePedido(p);
	}
	
	@Test
	public void testGetPuntajePedido2() {
		Cliente cli = new Cliente("Jor", "123jorge", "Jorge");
		Pedido p = new Pedido(cli, 1, true, false, 20, "ZONA_STANDARD");
		int actual = m.getPuntajePedido(p);
		assertNull(actual);
	}
	
	@Test
	public void testGetPuntajePedido3() {
		Cliente cli = new Cliente("Jor", "123jorge", "Jorge");
		Pedido p = new Pedido(cli, 1, false, true, 20, "ZONA_STANDARD");
		int actual = m.getPuntajePedido(p);
		assertNull(actual);
	}
	
	@Test
	public void testGetPuntajePedido4() {
		Cliente cli = new Cliente("Jor", "123jorge", "Jorge");
		Pedido p = new Pedido(cli, 2, false, false, 20, "ZONA_STANDARD");
		int actual = m.getPuntajePedido(p);
		assertNull(actual);
	}
}

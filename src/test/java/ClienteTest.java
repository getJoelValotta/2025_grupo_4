package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.Cliente;

public class ClienteTest {
	Cliente c = new Cliente("JJorge", "AAAA", "Jorge");
	
	@Test
	public void testCliente() {
		assertNotNull(c);
	}

	@Test
	public void testGetNombreReal() {
		String esperado = "Jorge";
		String actual = c.getNombreReal();
		assertEquals(esperado, actual);
	}

}

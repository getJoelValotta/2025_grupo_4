package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.*;

public class ChoferTest {
	Chofer c = new ChoferTemporario("100000", "Jorge");

	@Test
	public void testChofer() {
		assertNotNull(c);
	}
	
	@Test
	public void testGetDni() {
		String esperado = "100000";
		String actual = c.getDni();
		assertEquals(esperado, actual);
	}
	
	@Test
	public void testGetNombre() {
		String esperado = "Jorge";
		String actual = c.getNombre();
		assertEquals(esperado, actual);
	}

	@Test
	public void testGetSueldoBasico() {
		double esperado = 500000;
		double actual = Chofer.getSueldoBasico();
		assertEquals(esperado, actual,0.1);
	}
	
	@Test
	public void testSetSueldoBasico() {
		Chofer.setSueldoBasico(600000);
		double esperado = 600000;
		double actual = Chofer.getSueldoBasico();
		assertEquals(esperado, actual,0.1);
		Chofer.setSueldoBasico(500000);
	}
	
	@Test
	public void testGetSueldoBruto() {
		double esperado = 500000;
		double actual = c.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}
	
	@Test
	public void testGetSueldoNeto() {
		double esperado = 430000;
		double actual = c.getSueldoNeto();
		assertEquals(esperado, actual,0.1);
	}
	
}

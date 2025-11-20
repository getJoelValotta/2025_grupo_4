package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.ChoferPermanente;

public class ChoferPermanenteTest {

	@Test
	public void testChoferPermanente1() {
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 1900, 0);
		assertNotNull(c);
	}
	
	@Test
	public void testChoferPermanente2() {
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 3000, 0);
		assertNotNull(c);
	}


	@Test
	public void testGetSueldoBruto1() {  //test con 0 hijos y 19 anios de antiguedad
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 2006, 0);
		double esperado = ChoferPermanente.getSueldoBasico() + ChoferPermanente.getSueldoBasico() * 0.05 * 19;
		double actual = c.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}

	@Test
	public void testGetSueldoBruto2() {  //test con 0 hijos y 30 anios de antiguedad
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 1995, 0);
		double esperado = ChoferPermanente.getSueldoBasico() + ChoferPermanente.getSueldoBasico();
		double actual = c.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}
	
	@Test
	public void testGetSueldoBruto3() {  //test con 3 hijos y 0 anios de antiguedad
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 2025, 3);
		double esperado = ChoferPermanente.getSueldoBasico() + ChoferPermanente.getSueldoBasico() * 0.07 * 3;
		double actual = c.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}

	@Test
	public void testGetCantidadHijos() {
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 2025, 0);
		int esperado = 0;
		int actual = c.getCantidadHijos();
		assertEquals(esperado, actual);
	}
	
	@Test
	public void testSetCantidadHijos() {
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 2025, 0);
		c.setCantidadHijos(3);
		int esperado = 3;
		int actual = c.getCantidadHijos();
		assertEquals(esperado,actual);
	}
	
	
	@Test
	public void testGetAntiguedad() {
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 1990, 0);
		int esperado = 35;
		int actual = c.getAntiguedad();
		assertEquals(esperado,actual);
	}
	
	@Test
	public void testGetAnioIngreso() {
		ChoferPermanente c = new ChoferPermanente("100", "Jorge", 1990, 0);
		int esperado = 1990;
		int actual = c.getAnioIngreso();
		assertEquals(esperado,actual);
	}
}

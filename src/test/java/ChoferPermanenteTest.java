import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.ChoferPermanente;

public class ChoferPermanenteTest {
	ChoferPermanente c = new ChoferPermanente("44444444", "Jorge", 1990, 3);

	@Test
	public void testChoferPermanente() {
		assertNotNull(c);
	}

	@Test
	public void testGetCantidadHijos() {
		int esperado = 3;
		int actual = c.getCantidadHijos();
		assertEquals(esperado, actual);
	}

	@Test
	public void testSetCantidadHijos() {
		c.setCantidadHijos(4);
		int esperado = 4;
		int actual = c.getCantidadHijos();
		assertEquals(esperado,actual);
	}

	@Test
	public void testGetAntiguedad() {
		int esperado = 35;
		int actual = c.getAntiguedad();
		assertEquals(esperado,actual);
	}


	@Test
	public void testGetSueldoBruto1() {  //test con 3 hijos y mas de 20 anios de antiguedad
		double esperado = ChoferPermanente.getSueldoBasico() * 2 + ChoferPermanente.getSueldoBasico() * 0.07 * 3;
		double actual = c.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}

	@Test
	public void testGetSueldoBruto2() { //test con 3 hijos y menos de 20 de antiguedad
		ChoferPermanente d = new ChoferPermanente("44444444", "Jorge", 2023, 3);
		double esperado = ChoferPermanente.getSueldoBasico() + ChoferPermanente.getSueldoBasico() * 0.05 * 2 + ChoferPermanente.getSueldoBasico() * 0.07 * 3;
		double actual = d.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}
	
	@Test
	public void testGetSueldoBruto3() { //test con 3 hijos y 20 anios de antiguedad
		ChoferPermanente d = new ChoferPermanente("44444444", "Jorge", 2023, 3);
		double esperado = ChoferPermanente.getSueldoBasico() * 2 + ChoferPermanente.getSueldoBasico() * 0.07 * 3;
		double actual = d.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}
	
	@Test
	public void testGetSueldoBruto4() { //test sin hijos y mas de 20 anios de antiguedad
		ChoferPermanente d = new ChoferPermanente("44444444", "Jorge", 1990, 0);
		double esperado = ChoferPermanente.getSueldoBasico() * 2 + ChoferPermanente.getSueldoBasico() * 0.07 * 0;
		double actual = d.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}
	
	@Test
	public void testGetSueldoBruto5() { //test sin hijos y menos de 20 de antiguedad
		ChoferPermanente d = new ChoferPermanente("44444444", "Jorge", 2023, 0);
		double esperado = ChoferPermanente.getSueldoBasico() + ChoferPermanente.getSueldoBasico() * 0.05 * 2 + ChoferPermanente.getSueldoBasico() * 0.07 * 0;
		double actual = d.getSueldoBruto();
		assertEquals(esperado, actual,0.1);
	}


}

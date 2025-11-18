import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.ChoferTemporario;

public class ChoferTemporarioTest {
	ChoferTemporario c = new ChoferTemporario("44444444", "Jorge");
	
	@Test
	public void testChoferTemporario() {
		assertNotNull(c);
	}

	@Test
	public void testGetSueldoBruto() {
		double actual = c.getSueldoBruto();
		double esperado = ChoferTemporario.getSueldoBasico();
		assertEquals(esperado,actual,0.1);
	}
}

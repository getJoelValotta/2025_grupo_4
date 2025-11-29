package modeloDatosTest;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.ChoferTemporario;

public class ChoferTemporarioTest {
	ChoferTemporario c = new ChoferTemporario("100", "Jorge");
	
	@Test
	public void testChoferTemporario() {
		assertNotNull(c);
	}
}

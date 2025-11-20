package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.*;

public class AdministradorTest{

	@Test
	public void testAdministrador1() {              //Consultar que testear ya que casi no tiene metodos
		Administrador instancia1 = Administrador.getInstance();
		assertNotNull(instancia1);
	}
	
	@Test
	public void testAdministrador2() {              //Consultar que testear ya que casi no tiene metodos
		Administrador instancia1 = Administrador.getInstance();
		Administrador instancia2 = Administrador.getInstance();
		assertSame(instancia1, instancia2);
	}

}

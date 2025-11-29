package modeloDatosTest;

import static org.junit.Assert.*;

import org.junit.Test;

import modeloDatos.Viaje;

import modeloDatos.*;

public class ViajeTest {
	Cliente cli = new Cliente("Jor", "123jorge", "Jorge");
	Pedido p = new Pedido(cli, 2, false, false, 20, "ZONA_STANDARD");
	Chofer cho = new ChoferTemporario("100", "Juan");
	Auto a = new Auto("AAAA", 4, true);
	Viaje v = new Viaje(p, cho, a);

	@Test
	public void testChofer() {
		assertNotNull(v);
	}
	
	@Test
	public void testGetValorBase() {
		double esperado = 1000;
		double actual = Viaje.getValorBase();
		assertEquals(esperado,actual,0.1);
	}
	
	@Test
	public void testSetValorBase() {
		Viaje.setValorBase(2000);
		double esperado = 2000;
		double actual = Viaje.getValorBase();
		assertEquals(esperado,actual,0.1);
		Viaje.setValorBase(1000);
	}
	
	@Test
	public void testGetPedido() {
		Pedido esperado = p;
		Pedido actual = v.getPedido();
		assertSame(esperado,actual);
	}
	
	@Test
	public void testGetChofer() {
		Chofer esperado = cho;
		Chofer actual = v.getChofer();
		assertSame(esperado,actual);
	}
	
	@Test
	public void testGetVehiculo() {
		Vehiculo esperado = a;
		Vehiculo actual = v.getVehiculo();
		assertSame(esperado,actual);
	}
	
	@Test
	public void testIsFinalizado() {
		boolean esperado = false;
		boolean actual = v.isFinalizado();
		assertEquals(esperado,actual);
	}
	
	@Test
	public void testFinalizarViaje1() {
		Viaje va = new Viaje(p, cho, a);
		va.finalizarViaje(0);
		boolean finalizacionEsperada = true;
		boolean finalizacionActual = va.isFinalizado();
		int calificacionEsperada = 0;
		int calificacionActual = va.getCalificacion();
		assertEquals(finalizacionEsperada,finalizacionActual);
		assertEquals(calificacionEsperada,calificacionActual);
	}
	
	@Test
	public void testFinalizarViaje2() {
		Viaje vb = new Viaje(p, cho, a);
		vb.finalizarViaje(5);
		boolean finalizacionEsperada = true;
		boolean finalizacionActual = vb.isFinalizado();
		int calificacionEsperada = 5;
		int calificacionActual = vb.getCalificacion();
		assertEquals(finalizacionEsperada,finalizacionActual);
		assertEquals(calificacionEsperada,calificacionActual);
	}
	
	@Test
	public void testGetCalificaion() {
		Viaje vc = new Viaje(p, cho, a);
		vc.finalizarViaje(5);
		int calificacionEsperada = 5;
		int calificacionActual = vc.getCalificacion();
		assertEquals(calificacionEsperada,calificacionActual);
	}

	@Test
	public void testGetValor1() {
		Pedido p = new Pedido(cli, 2, false, false, 20, "ZONA_STANDARD");
		Viaje v1 = new Viaje(p,cho,a);
		double esperado = 3200;
		double actual = v1.getValor();
		assertEquals(esperado,actual,0.1);
	}
	
	@Test
	public void testGetValor2() {
		Pedido p = new Pedido(cli, 2, false, false, 20, "ZONA_SIN_ASFALTAR");
		Viaje v2 = new Viaje(p,cho,a);
		double esperado = 4400;
		double actual = v2.getValor();
		assertEquals(esperado,actual,0.1);
	}
	
	@Test
	public void testGetValor3() {
		Pedido p = new Pedido(cli, 2, false, false, 20, "ZONA_PELIGROSA");
		Viaje v3 = new Viaje(p,cho,a);
		double esperado = 5200;
		double actual = v3.getValor();
		assertEquals(esperado,actual,0.1);
	}
	
	@Test
	public void testGetValor4() {
		Pedido p = new Pedido(cli, 2, true, false, 20, "ZONA_STANDARD");
		Viaje v4 = new Viaje(p,cho,a);
		double esperado = 7400;
		double actual = v4.getValor();
		assertEquals(esperado,actual,0.1);
	}
	
	@Test
	public void testGetValor5() {
		Pedido p = new Pedido(cli, 2, false, true, 20, "ZONA_STANDARD");
		Viaje v5 = new Viaje(p,cho,a);
		double esperado = 4400;
		double actual = v5.getValor();
		assertEquals(esperado,actual,0.1);
	}
}

package test;


import static org.junit.Assert.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import controlador.Controlador;
import org.junit.Assert;
import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Mensajes;
import vista.Ventana;
import static util.Constantes.ZONA_PELIGROSA;
import static util.Constantes.NOMBRE_USUARIO;
import static util.Constantes.PASSWORD;
import static util.Constantes.LOGIN;
import static util.Constantes.DNI_CHOFER;
import static util.Constantes.NOMBRE_CHOFER;
import static util.Constantes.TEMPORARIO;
import static util.Constantes.NUEVO_CHOFER;
import static util.Constantes.CH_ANIO;
import static util.Constantes.CH_CANT_HIJOS;
import static util.Constantes.PERMANENTE;
import static util.Constantes.LISTA_CHOFERES_TOTALES;
import static util.Constantes.PATENTE;
import static util.Constantes.NUEVO_VEHICULO;
import static util.Constantes.MOTO;
import static util.Constantes.LISTA_VEHICULOS_TOTALES;
import static util.Constantes.AUTO;
import static util.Constantes.CANTIDAD_PLAZAS;
import static util.Constantes.COMBI;
import static util.Constantes.LISTA_CHOFERES_LIBRES;
import static util.Constantes.LISTA_VIAJES_HISTORICOS;
import static util.Constantes.LISTA_PEDIDOS_PENDIENTES;
import static util.Constantes.LISTADO_DE_CLIENTES;

public class GuiTestAdministrador {

	static Robot robot;
	Controlador controlador;
	FalsoOptionPane pane = new FalsoOptionPane();
	static Empresa empresa = Empresa.getInstance();
	

	@BeforeClass
    public static void setUpClass() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

	@Before
	public void setUp() throws Exception
	{
		controlador = new Controlador();
		empresa = Empresa.getInstance();
		controlador.getVista().setOptionPane(pane);
		empresa.agregarCliente("Jojo","123456","Valotta Jojo");
		Cliente cliente1 = empresa.getClientes().get("Jojo");
		ChoferTemporario chofer1 = new ChoferTemporario("33222444","Nahue");
		Combi vehiculo1 = new Combi("AG321IT",9,false);
		empresa.agregarChofer(chofer1);
		empresa.agregarVehiculo(vehiculo1);
		empresa.setUsuarioLogeado(cliente1);
		Pedido pedido1;
		
		for(int i=0;i<3;i++)
		{
			pedido1  = new Pedido(cliente1, 4, false, true, 50*i, ZONA_PELIGROSA);
			empresa.agregarPedido(pedido1);
			empresa.crearViaje(pedido1, chofer1, vehiculo1);
			empresa.pagarYFinalizarViaje(4);
		}
		pedido1  = new Pedido(cliente1, 4, false, true, 50, ZONA_PELIGROSA);
		empresa.agregarPedido(pedido1);
		empresa.logout();
		
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_USUARIO);
		JTextField pass = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PASSWORD);
		JButton aceptarLogin = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), LOGIN);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("admin", robot);
		TestUtils.clickComponent(pass, robot);
		TestUtils.tipeaTexto("admin", robot);
		TestUtils.clickComponent(aceptarLogin, robot);
	}


	@After
	public void tearDown()
	{
		Ventana ventana = (Ventana) controlador.getVista();
		ventana.setVisible(false);
		empresa.setClientes(new HashMap<String,Cliente>());
		empresa.setChoferes(new HashMap<String,Chofer>());
		empresa.setChoferesDesocupados(new ArrayList<Chofer>());
		empresa.setVehiculos(new HashMap<String,Vehiculo>());
		empresa.setVehiculosDesocupados(new ArrayList<Vehiculo>());
		empresa.setPedidos(new HashMap<Cliente,Pedido>());
		empresa.setViajesTerminados(new ArrayList<Viaje>());
	}
	
	@Test
	public void testNuevoChoferTemporario1(){ //Se prueba si el boton esta disponible habiendo un caracter en cada campo
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), TEMPORARIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    
	    TestUtils.clickComponent(temporario, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("1", robot);
	    
	    Assert.assertTrue(nuevoChofer.isEnabled());
	}

	@Test
	public void testSoloDNI(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), TEMPORARIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    
	    TestUtils.clickComponent(temporario, robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("1", robot);
	    
	    Assert.assertTrue(!nuevoChofer.isEnabled());
	}
	
	@Test
	public void testSoloNombre(){
		JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), TEMPORARIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    
	    TestUtils.clickComponent(temporario, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    
	    Assert.assertTrue(!nuevoChofer.isEnabled());
	}
	
	@Test
	public void testChofCorrecto(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), TEMPORARIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    JList<?> listaChoferes = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_CHOFERES_TOTALES);    
	    boolean flag = false;
	    
	    TestUtils.clickComponent(temporario, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("Nahue", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("33222444", robot);
	    TestUtils.clickComponent(nuevoChofer, robot);
	    
	    for (int i = 0; i < listaChoferes.getModel().getSize() && !flag; i++) {
	        Chofer choferActual = (Chofer) listaChoferes.getModel().getElementAt(i);
	        if (choferActual.getDni().equals("33222444")) {
	            flag = true;
	        }
	    }

	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("El JList de choferes deberia contener al nuevo chofer", flag),
	        new SimpleEntry<>("El JTextField dni chofer deberia estar vacio", dniChofer.getText().isEmpty()),
	        new SimpleEntry<>("El JTextField nombre de chofer deberia estar vacio", nombreChofer.getText().isEmpty())
	    );

	    manejarAsertos(aserciones);
	    
	}
	
	@Test
	public void testChofCamposCompletos(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JTextField cantHijos = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_CANT_HIJOS);
	    JTextField anioIngreso = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_ANIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), PERMANENTE);
	    
	    
	    TestUtils.clickComponent(permanente, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(cantHijos, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(anioIngreso, robot);
	    TestUtils.tipeaTexto("2000", robot);
	    
	    Assert.assertTrue(nuevoChofer.isEnabled());
	}
	
	
	@Test
	public void testChofSinAnio(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JTextField cantHijos = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_CANT_HIJOS);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), PERMANENTE);
	    
	    
	    TestUtils.clickComponent(permanente, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(cantHijos, robot);
	    TestUtils.tipeaTexto("1", robot);
	    
	    Assert.assertTrue(!nuevoChofer.isEnabled());
	}
	
	@Test
	public void testChofSinHijos(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JTextField anioIngreso = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_ANIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), PERMANENTE);
	    
	    
	    TestUtils.clickComponent(permanente, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(anioIngreso, robot);
	    TestUtils.tipeaTexto("2000", robot);
	    
	    Assert.assertTrue(!nuevoChofer.isEnabled());
	}
	
	@Test
	public void testChofSinDNI(){ 
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JTextField cantHijos = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_CANT_HIJOS);
	    JTextField anioIngreso = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_ANIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), PERMANENTE);
	    
	    
	    TestUtils.clickComponent(permanente, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    TestUtils.clickComponent(cantHijos, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(anioIngreso, robot);
	    TestUtils.tipeaTexto("2000", robot);
	    
	    Assert.assertTrue(!nuevoChofer.isEnabled());
	}
	
	@Test
	public void testChofSinNombre(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField cantHijos = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_CANT_HIJOS);
	    JTextField anioIngreso = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_ANIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), PERMANENTE);
	    
	    
	    TestUtils.clickComponent(permanente, robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("a", robot);
	    TestUtils.clickComponent(cantHijos, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(anioIngreso, robot);
	    TestUtils.tipeaTexto("2000", robot);
	    
	    Assert.assertTrue(!nuevoChofer.isEnabled());
	}
	
	@Test
	public void testChofCompleto(){ 
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JTextField cantHijos = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_CANT_HIJOS);
	    JTextField anioIngreso = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CH_ANIO);
	    JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), PERMANENTE);
	    JList<?> listaChoferes = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_CHOFERES_TOTALES);
	    boolean flag = false;
	    
	    TestUtils.clickComponent(permanente, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("MIli", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("44444444", robot);
	    TestUtils.clickComponent(cantHijos, robot);
	    TestUtils.tipeaTexto("1", robot);
	    TestUtils.clickComponent(anioIngreso, robot);
	    TestUtils.tipeaTexto("2000", robot);
	    
	    for (int i = 0; i < listaChoferes.getModel().getSize() && !flag; i++) {
	        Chofer choferActual = (Chofer) listaChoferes.getModel().getElementAt(i);
	        if (choferActual.getDni().equals("MIli")) {
	            flag = true;
	        }
	    }

	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("El JList de choferes deberia contener al nuevo chofer", flag),
	        new SimpleEntry<>("El JTextField dni chofer deberia estar vacio", dniChofer.getText().isEmpty()),
	        new SimpleEntry<>("El JTextField nombre de chofer deberia estar vacio", nombreChofer.getText().isEmpty()),
	        new SimpleEntry<>("El JTextField cantidad de hijos de chofer deberia estar vacio", cantHijos.getText().isEmpty()),
	        new SimpleEntry<>("El JTextField anio ingreso de chofer deberia estar vacio", anioIngreso.getText().isEmpty())
	    );

	    manejarAsertos(aserciones);
	}
	
	@Test
	public void chofRepetido(){
		JTextField dniChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), DNI_CHOFER);
	    JTextField nombreChofer = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_CHOFER);
	    JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), TEMPORARIO);
	    JButton nuevoChofer = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_CHOFER);
	    
	    TestUtils.clickComponent(temporario, robot);
	    TestUtils.clickComponent(nombreChofer, robot);
	    TestUtils.tipeaTexto("MIli", robot);
	    TestUtils.clickComponent(dniChofer, robot);
	    TestUtils.tipeaTexto("44444444", robot);
	    
	    assertEquals("Deberia mostrar"+Mensajes.CHOFER_YA_REGISTRADO.getValor(), Mensajes.CHOFER_YA_REGISTRADO.getValor(), pane.getMensaje());
	}

	@Test
	public void testMotoSoloPatente(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JRadioButton moto= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), MOTO);
		
		TestUtils.clickComponent(moto, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("A", robot);
		
		Assert.assertTrue(nuevoVehiculo.isEnabled());
	}
	
	@Test
	public void testContieneMoto(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JRadioButton moto= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), MOTO);
		 JList<?> listaVehiculos = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_VEHICULOS_TOTALES);
		boolean flag = false;
		
		TestUtils.clickComponent(moto, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("PA333TE", robot);
		TestUtils.clickComponent(nuevoVehiculo, robot);
		
		for (int i = 0; i < listaVehiculos.getModel().getSize() && !flag; i++) {
	        Vehiculo vehiculoActual = (Vehiculo) listaVehiculos.getModel().getElementAt(i);
	        if (vehiculoActual.getPatente().equals("PA333TE")) {
	            flag = true;
	        }
	    }
		
		List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
		        new SimpleEntry<>("El JList de vehiculos deberia contener la nueva moto", flag),
		        new SimpleEntry<>("El JTextField patente deberia estar vacio", patente.getText().isEmpty())
		    );

		    manejarAsertos(aserciones);
	}
	
	@Test
	public void testAutoCompleto(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JTextField cantPlazas = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CANTIDAD_PLAZAS);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JRadioButton auto= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), AUTO);
		
		TestUtils.clickComponent(auto, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(cantPlazas, robot);
		TestUtils.tipeaTexto("1", robot);
		
		Assert.assertTrue(nuevoVehiculo.isEnabled());
	}
	
	@Test
	public void testContieneAuto(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JTextField cantPlazas = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CANTIDAD_PLAZAS);
		JRadioButton auto= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), AUTO);
		JList<?> listaVehiculos = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_VEHICULOS_TOTALES);
		boolean flag = false;
		
		TestUtils.clickComponent(auto, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("PA222TE", robot);
		TestUtils.clickComponent(cantPlazas, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nuevoVehiculo, robot);
		
		for (int i = 0; i < listaVehiculos.getModel().getSize() && !flag; i++) {
	        Vehiculo vehiculoActual = (Vehiculo) listaVehiculos.getModel().getElementAt(i);
	        if (vehiculoActual.getPatente().equals("PA222TE")) {
	            flag = true;
	        }
	    }
		
		List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
		        new SimpleEntry<>("El JList de vehiculos deberia contener al nuevo auto", flag),
		        new SimpleEntry<>("El JTextField patente deberia estar vacio", patente.getText().isEmpty()),
		        new SimpleEntry<>("El JTextField cantidad de plazas deberia estar vacio", cantPlazas.getText().isEmpty())
		    );

		    manejarAsertos(aserciones);
	}
	
	@Test
	public void testCombiCompleta(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JTextField cantPlazas = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CANTIDAD_PLAZAS);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JRadioButton combi= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), COMBI);
		
		TestUtils.clickComponent(combi, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(cantPlazas, robot);
		TestUtils.tipeaTexto("1", robot);
		
		Assert.assertTrue(nuevoVehiculo.isEnabled());
	}
	
	@Test
	public void testContieneCombi(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JTextField cantPlazas = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CANTIDAD_PLAZAS);
		JRadioButton auto= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), COMBI);
		JList<?> listaVehiculos = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_VEHICULOS_TOTALES);
		boolean flag = false;
		
		TestUtils.clickComponent(auto, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("PA111TE", robot);
		TestUtils.clickComponent(cantPlazas, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nuevoVehiculo, robot);
		
		for (int i = 0; i < listaVehiculos.getModel().getSize() && !flag; i++) {
	        Vehiculo vehiculoActual = (Vehiculo) listaVehiculos.getModel().getElementAt(i);
	        if (vehiculoActual.getPatente().equals("PA111TE")) {
	            flag = true;
	        }
	    }
		
		List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
		        new SimpleEntry<>("El JList de vehiculos deberia contener al nuevo auto", flag),
		        new SimpleEntry<>("El JTextField patente deberia estar vacio", patente.getText().isEmpty()),
		        new SimpleEntry<>("El JTextField cantidad de plazas deberia estar vacio", cantPlazas.getText().isEmpty())
		    );

		    manejarAsertos(aserciones);
	}
	
	@Test
	public void testPatenteRepetida(){
		JTextField patente = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PATENTE);
		JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), NUEVO_VEHICULO);
		JTextField cantPlazas = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), CANTIDAD_PLAZAS);
		JRadioButton auto= (JRadioButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), COMBI);
		
		TestUtils.clickComponent(auto, robot);
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("PA111TE", robot);
		TestUtils.clickComponent(cantPlazas, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nuevoVehiculo, robot);
		
		assertEquals("Deberia mostrar"+Mensajes.VEHICULO_YA_REGISTRADO.getValor(), Mensajes.VEHICULO_YA_REGISTRADO.getValor(), pane.getMensaje());
	}
	
	

	@Test
	public void testListadoClientes() {
	    JList<?> listaClientes = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTADO_DE_CLIENTES);
	    Cliente cliente = (Cliente) listaClientes.getModel().getElementAt(0);
	    
	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("Cantidad incorrecta de clientes totales", listaClientes.getModel().getSize() == 1),
	        new SimpleEntry<>("Cliente incorrecto", "Jojo".equals(cliente.getNombreUsuario()))
	    );

	    manejarAsertos(aserciones);
	}

	@Test
	public void testListadoVehiculos() {
	    JList<?> listaVehiculos = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_VEHICULOS_TOTALES);
	    Vehiculo vehiculo = (Vehiculo) listaVehiculos.getModel().getElementAt(0);
	    
	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("Cantidad incorrecta de vehiculos totales", listaVehiculos.getModel().getSize() == 1),
	        new SimpleEntry<>("Vehiculo incorrecto", "AG321IT".equals(vehiculo.getPatente()))
	    );

	    manejarAsertos(aserciones);
	}

	@Test
	public void testListadoChoferes() {
	    JList<?> listaChoferes = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_CHOFERES_TOTALES);
	    Chofer chofer = (Chofer) listaChoferes.getModel().getElementAt(0);
	    
	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("Cantidad incorrecta de choferes totales", listaChoferes.getModel().getSize() == 1),
	        new SimpleEntry<>("Chofer incorrecto", "33222444".equals(chofer.getDni()))
	    );

	    manejarAsertos(aserciones);
	}

	@Test
	public void testListadoChoferesLibres() {
	    JList<?> listaChoferesLibres = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_CHOFERES_LIBRES);
	    Chofer chofer = (Chofer) listaChoferesLibres.getModel().getElementAt(0);
	    
	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("Cantidad incorrecta de choferes libres", listaChoferesLibres.getModel().getSize() == 1),
	        new SimpleEntry<>("Chofer libre incorrecto", "33222444".equals(chofer.getDni()))
	    );

	    manejarAsertos(aserciones);
	}

	@Test
	public void testListadoPedidosPendientes() {
	    JList<?> listaPedidos = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_PEDIDOS_PENDIENTES);
	    Pedido pedido = (Pedido) listaPedidos.getModel().getElementAt(0);
	    
	    List<SimpleEntry<String, Boolean>> aserciones = Arrays.asList(
	        new SimpleEntry<>("Cantidad incorrecta de pedidos totales", listaPedidos.getModel().getSize() == 1),
	        new SimpleEntry<>("Pedido incorrecto", "Jojo".equals(pedido.getCliente().getNombreUsuario()))
	    );

	    manejarAsertos(aserciones);
	}

	@Test
	public void testListadoViajesTerminados() {
	    JList<?> listaViajesTerminados = (JList<?>) TestUtils.getComponentForName((Ventana)controlador.getVista(), LISTA_VIAJES_HISTORICOS);
	    
	    List<SimpleEntry<String, Boolean>> aserciones = new ArrayList<>();
	    for (int i = 0; i < listaViajesTerminados.getModel().getSize(); i++) {
	        Viaje viaje = (Viaje) listaViajesTerminados.getModel().getElementAt(i);
	        Pedido pedido = viaje.getPedido();
	        
	        aserciones.add(new SimpleEntry<>("No coinciden los pasajeros del viaje terminado", pedido.getCantidadPasajeros() == 4));
	        aserciones.add(new SimpleEntry<>("No coinciden los km del viaje terminado", pedido.getKm() == 50 * i));
	        aserciones.add(new SimpleEntry<>("No coinciden la mascota del viaje terminado", !pedido.isMascota()));
	        aserciones.add(new SimpleEntry<>("No coinciden el baul del viaje terminado", pedido.isBaul()));
	        aserciones.add(new SimpleEntry<>("No coinciden la zona del viaje terminado", ZONA_PELIGROSA.equals(pedido.getZona())));
	    }
	    
	    manejarAsertos(aserciones);
	}
	
	public static void manejarAsertos(List<SimpleEntry<String, Boolean>> aserciones) {
		StringBuilder errores = new StringBuilder();
		for (SimpleEntry<String, Boolean> asercion : aserciones) {
			if (!asercion.getValue()) {
				errores.append(asercion.getKey()).append("\n");
			}
		}
		
		if (errores.length() > 0) {
			Assert.fail("Errores encontrados:\n" + errores.toString());
		}
	}
}

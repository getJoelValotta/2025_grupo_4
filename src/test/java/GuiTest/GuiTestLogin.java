package GuiTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.Constantes.LOGIN;
import static util.Constantes.NOMBRE_USUARIO;
import static util.Constantes.PASSWORD;
import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.*;

import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;



public class GuiTestLogin {
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
	    try {
	    	empresa.agregarCliente("bulo", "123456", "Aristobulo Cecive");
			empresa.agregarCliente("Favio", "456789", "Milagros Fazio");
			empresa.agregarCliente("Chivo", "123789", "Francesa Chivo");
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp(){
		this.controlador = new Controlador();
		this.controlador.getVista().setOptionPane(pane);
		 
	}
	
	@After
	public void tearDown() {
		Ventana ventana = (Ventana) controlador.getVista();
		ventana.setVisible(false);
	}
	
	@Test
	public void testPassIncorrecto()
	{
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_USUARIO);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PASSWORD);
		JButton login = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), LOGIN);
	
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("bulo", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("200000", robot);
		TestUtils.clickComponent(login, robot);
		
		assertEquals("Deberia mostrar"+Mensajes.PASS_ERRONEO.getValor(), Mensajes.PASS_ERRONEO.getValor(), pane.getMensaje());
	}
	
	@Test
	public void testUserIncorrecto()
	{
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_USUARIO);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PASSWORD);
		JButton login = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), LOGIN);
	
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("LoloProd", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("123456", robot);
		TestUtils.clickComponent(login, robot);
		
		assertEquals("Deberia mostrar"+Mensajes.USUARIO_DESCONOCIDO.getValor(), Mensajes.USUARIO_DESCONOCIDO.getValor(), pane.getMensaje());
	}
	
	@Test
	public void testUserCorrecto()
	{
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), NOMBRE_USUARIO);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), PASSWORD);
		JButton login = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), LOGIN);
	
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("Favio", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("456789", robot);
		TestUtils.clickComponent(login, robot);
		
		JPanel panelCliente = (JPanel) TestUtils.getComponentForName((Ventana)controlador.getVista(), Constantes.PANEL_CLIENTE);
		assertTrue("Deberia acceder al panel cliente", panelCliente != null);
		assertEquals("Nombre real incorecto", "CincoHoras DeSufrimiento", ((TitledBorder) panelCliente.getBorder()).getTitle());
	}
	
//_______________________________________TEST ENABLE/DISABE________________________________________________________________
	@Test
	public void testLogSoloNombre()
	{
		robot.delay(TestUtils.getDelay());
		// obtengo las referencias a los componentes necesarios
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"NOMBRE_USUARIO");
		JButton aceptarReg = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"REGISTRAR");
		JButton aceptarLog = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(), "LOGIN");
		// lleno los JTextField
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("h", robot);
		// verifico los resultados
		Assert.assertTrue("El boton de registro deberia estar hablitado", aceptarReg.isEnabled());
		Assert.assertFalse("El boton de login deberia estar deshablitado", aceptarLog.isEnabled());
	}

	@Test
	public void testLogVacios()
	{
		// obtengo las referencias a los componentes necesarios
		JButton aceptarReg = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"REGISTRAR");
		JButton aceptarLog = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(), "LOGIN");
		// verifico los resultados
		Assert.assertTrue("El boton de registro deberia estar habilitado", aceptarReg.isEnabled());
		Assert.assertFalse("El boton de login deberia estar deshablitado", aceptarLog.isEnabled());
	}

	@Test
	public void testLogSoloContrasena()
	{
		robot.delay(TestUtils.getDelay());
		// obtengo las referencias a los componentes necesarios
		JTextField contra = (JTextField) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"PASSWORD");
		JButton aceptarReg = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"REGISTRAR");
		JButton aceptarLog = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(), "LOGIN");
		// lleno los JTextField
		TestUtils.clickComponent(contra, robot);
		TestUtils.tipeaTexto("h", robot);
		// verifico los resultados
		Assert.assertTrue("El boton de registro deberia estar hablitado", aceptarReg.isEnabled());
		Assert.assertFalse("El boton de login deberia estar deshablitado", aceptarLog.isEnabled());
	}
	
	@Test
	public void testLogDosLlenos()
	{
		robot.delay(TestUtils.getDelay());
		// obtengo las referencias a los componentes necesarios
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"NOMBRE_USUARIO");
		JTextField contra = (JTextField) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"PASSWORD");
		

		JButton aceptarReg = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(),
				"REGISTRAR");
		JButton aceptarLog = (JButton) TestUtils.getComponentForName((Ventana) controlador.getVista(), "LOGIN");
		// lleno los JTextField
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("h", robot);
		TestUtils.clickComponent(contra, robot);
		TestUtils.tipeaTexto("h", robot);

		// verifico los resultados
		Assert.assertTrue("El boton de registro deberia estar hablitado", aceptarReg.isEnabled());
		Assert.assertTrue("El boton de login deberia estar hablitado", aceptarLog.isEnabled());
	}
	
	
//_______________________________________________________________________________________________________
}


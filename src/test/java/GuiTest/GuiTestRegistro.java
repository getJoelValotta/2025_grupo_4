package GuiTest;


import static org.junit.Assert.*;
import static util.Constantes.REG_USSER_NAME;
import static util.Constantes.REG_REAL_NAME;
import static util.Constantes.REG_PASSWORD;
import static util.Constantes.REG_CONFIRM_PASSWORD;
import static util.Constantes.REG_BUTTON_REGISTRAR;
import static util.Constantes.PANEL_LOGIN;
import static util.Constantes.REGISTRAR;
import static util.Constantes.REG_BUTTON_CANCELAR;
import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;
import util.Mensajes;
import vista.Ventana;


public class GuiTestRegistro {

	static Robot robot;
	Controlador controlador;
	FalsoOptionPane pane = new FalsoOptionPane();
	static Empresa empresa = Empresa.getInstance();
	
	@BeforeClass
    public static void setUpClass(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        try {
			empresa.agregarCliente("Jojo","123456","Valotta Jojo");
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
    }
	
	@Before
	public void setUp(){	
		this.controlador = new Controlador();
		this.controlador.getVista().setOptionPane(pane);
		JButton registrar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REGISTRAR);
		TestUtils.clickComponent(registrar,robot);
		 
	}
		
	@After
	public void tearDown(){
		Ventana ventana = (Ventana) controlador.getVista();
		ventana.setVisible(false);
	}

	@Test
	public void testRegistroExitoso(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_REAL_NAME);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(),REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(),REG_BUTTON_REGISTRAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("bulo", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("456789", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("456789", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("Aristo bulo", robot);
		TestUtils.clickComponent(aceptar, robot);
		
		robot.delay(TestUtils.getDelay());
		JPanel panelLogin = (JPanel) TestUtils.getComponentForName((Ventana)controlador.getVista(),PANEL_LOGIN);
		assertTrue("Deberia haber vuelto al login", panelLogin != null);
	}
	
	@Test
	public void testContrasenasNoCoinciden(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_REAL_NAME);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("bulo", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("123456", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("456789", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("Aristo bulo", robot);
		TestUtils.clickComponent(aceptar, robot);	
		
		assertEquals("Deberia mostrar"+Mensajes.PASS_NO_COINCIDE.getValor(), Mensajes.PASS_NO_COINCIDE.getValor(), pane.getMensaje());	
	}
	
	@Test
	public void testCancelarRegistro(){
		robot.delay(TestUtils.getDelay());
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		TestUtils.clickComponent(cancelar, robot);
		robot.delay(TestUtils.getDelay());
		JPanel Login = (JPanel) TestUtils.getComponentForName((Ventana)controlador.getVista(), PANEL_LOGIN);
		assertTrue("Deberia haber vuelto al login", Login != null);
	}

	@Test
	public void testRegistroUsuarioRepetido(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(),REG_REAL_NAME);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(),REG_BUTTON_REGISTRAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("Jojo", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("123456", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("123456", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("Valotta Jojo", robot);
		TestUtils.clickComponent(aceptar, robot);	
		
		assertEquals("Deberia mostrar"+Mensajes.USUARIO_REPETIDO.getValor(),Mensajes.USUARIO_REPETIDO.getValor(), pane.getMensaje() );
	}
	
	@Test
	public void testTextFileCompleto(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_REAL_NAME);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);	
		
		Assert.assertTrue(aceptar.isEnabled());	
		Assert.assertTrue(cancelar.isEnabled());
	}
	
	@Test
	public void testSinContraseña(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_REAL_NAME);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);

		
		Assert.assertFalse(aceptar.isEnabled());	
		Assert.assertTrue(cancelar.isEnabled());
	}
	
	@Test
	public void testSinUsuario(){
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_REAL_NAME);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		

		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);	
	
		
		Assert.assertFalse(aceptar.isEnabled());
		Assert.assertTrue(cancelar.isEnabled());
	}
	
	@Test
	public void testVacio(){
		robot.delay(TestUtils.getDelay());
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);

		Assert.assertFalse(aceptar.isEnabled());
		Assert.assertTrue(cancelar.isEnabled());
	}
	
	@Test
	public void testSoloUsuario(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("a", robot);
		
		Assert.assertFalse(aceptar.isEnabled());
		Assert.assertTrue(cancelar.isEnabled());
	}
	
	@Test
	public void testUsuarioYContraseña(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		
		Assert.assertFalse(aceptar.isEnabled());
		Assert.assertTrue(cancelar.isEnabled());
	}
	
	
	@Test
	public void testSinNombre(){
		robot.delay(TestUtils.getDelay());
		JTextField usuario = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_USSER_NAME);
		JTextField contrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_PASSWORD);
		JTextField repContrasena = (JTextField) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_CONFIRM_PASSWORD);
		JButton aceptar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_REGISTRAR);
		JButton cancelar = (JButton) TestUtils.getComponentForName((Ventana)controlador.getVista(), REG_BUTTON_CANCELAR);
		
		TestUtils.clickComponent(usuario, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(contrasena, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(repContrasena, robot);
		TestUtils.tipeaTexto("a", robot);

		Assert.assertFalse(aceptar.isEnabled());
		Assert.assertTrue(cancelar.isEnabled());
	}
	
}
package controlador;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.HashMap;


import org.mockito.Mockito;

// Imports de tu proyecto
import controlador.Controlador;
import excepciones.*;
import junit.framework.Assert;
import modeloDatos.Cliente;
import vista.IVista;
import vista.IOptionPane;
import modeloNegocio.Empresa;
import util.Mensajes;
@SuppressWarnings("deprecation")
public class controladorTestLoginLogoutAgregarCliente {
	private Controlador controladorReal;
    private Controlador controladorBajoPrueba; 
    private IVista vistaMock; 
    private IOptionPane optionPaneMock;
	

	@Before
	public void setUp() throws Exception {
		// 1. Creamos el Mock de la Vista
        this.vistaMock = Mockito.mock(IVista.class);
        this.optionPaneMock = Mockito.mock(IOptionPane.class);
        // 2. Creamos la instancia REAL del Controlador
        this.controladorReal = new Controlador();
        
        // 3. Inyectamos nuestro mock de vista en el objeto REAL
        this.controladorReal.setVista(this.vistaMock);

        // 4. Creamos el "espía"
        this.controladorBajoPrueba = Mockito.spy(this.controladorReal);
        when(this.vistaMock.getOptionPane()).thenReturn(this.optionPaneMock);
        
        Empresa.getInstance().logout();
        Empresa.getInstance().setClientes(new HashMap<>());
        
	}
	@Test
    public void testLogin1() {
        String usuario = "usuarioExistente";
        String usuarioInexistente = "usuarioInexistente";
        String pass = "passCualquiera";
        try {
        	Empresa.getInstance().agregarCliente(usuario, pass, pass);
        } catch (UsuarioYaExisteException e) {
        }
        
        when(this.vistaMock.getUsserName()).thenReturn(usuarioInexistente);
        when(this.vistaMock.getPassword()).thenReturn(pass);
        this.controladorBajoPrueba.login();
        
        verify(optionPaneMock).ShowMessage(Mensajes.USUARIO_DESCONOCIDO.getValor());
        verify(this.vistaMock, never()).logearUsuario();
        assertNull(Empresa.getInstance().getUsuarioLogeado());
       
    }
	@Test
    public void testLogin2() {
        String usuario = "usuarioExistente";
        String pass = "passCualquiera";
        String passErronea = "passErronea";

        try {
        	Empresa.getInstance().agregarCliente(usuario, pass, pass);
        } catch (UsuarioYaExisteException e) {
        }
        
        when(this.vistaMock.getUsserName()).thenReturn(usuario);
        when(this.vistaMock.getPassword()).thenReturn(passErronea);
        this.controladorBajoPrueba.login();
        
        
        verify(optionPaneMock).ShowMessage(Mensajes.PASS_ERRONEO.getValor());
        verify(this.vistaMock, never()).logearUsuario();
        assertNull(Empresa.getInstance().getUsuarioLogeado());
       
    }
	@Test
    public void testLogin3() {
        String usuario = "usuarioExistente";
        String pass = "passCualquiera";

       try {
        	Empresa.getInstance().agregarCliente(usuario, pass, pass);
        } catch (UsuarioYaExisteException e) {
        }
        when(this.vistaMock.getUsserName()).thenReturn(usuario);
        when(this.vistaMock.getPassword()).thenReturn(pass);	        
        
        this.controladorBajoPrueba.login();
        verify(optionPaneMock, never()).ShowMessage(anyString());
        
        assertNotNull(Empresa.getInstance().getUsuarioLogeado());
        assertTrue(Empresa.getInstance().getUsuarioLogeado().getNombreUsuario()==usuario);
       
    }
    @Test
    public void testLogout() {        
        String usuario = "usuarioParaLogout";
        String pass = "pass123";
        try {
            Empresa.getInstance().agregarCliente(usuario, pass, "Test Logout");
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("La preparación del test falló: " + e.getMessage());
        }
        
        doNothing().when(this.controladorBajoPrueba).escribir();

        this.controladorBajoPrueba.logout();
        assertNull("El usuario no se deslogueó, getUsuarioLogeado() no es nulo", 
                   Empresa.getInstance().getUsuarioLogeado());
        
        verify(this.controladorBajoPrueba, times(1)).escribir();
    }
	
    @Test
    public void testRegistrar1() {
        when(this.vistaMock.getRegNombreReal()).thenReturn("Usuario de Prueba");
        when(this.vistaMock.getRegUsserName()).thenReturn("usuarioPrueba");
        when(this.vistaMock.getRegPassword()).thenReturn("pass123");
        when(this.vistaMock.getRegConfirmPassword()).thenReturn("pass456");
        
        this.controladorBajoPrueba.registrar();
                
        verify(optionPaneMock).ShowMessage(Mensajes.PASS_NO_COINCIDE.getValor());
 
    }
    @Test
    public void testRegistrar2() {
        String usuarioExistente = "usuarioExistente";
        try {
            Empresa.getInstance().agregarCliente(usuarioExistente, "pass1", "Nombre");
        } catch (UsuarioYaExisteException e) {
        }
        when(this.vistaMock.getRegNombreReal()).thenReturn("Otro Nombre");
        when(this.vistaMock.getRegUsserName()).thenReturn(usuarioExistente);
        when(this.vistaMock.getRegPassword()).thenReturn("pass2");
        when(this.vistaMock.getRegConfirmPassword()).thenReturn("pass2"); 
        
        this.controladorBajoPrueba.registrar(); 

        verify(optionPaneMock).ShowMessage(Mensajes.USUARIO_REPETIDO.getValor());
    }
	@Test
    public void testRegistrar3() {
        String nuevoUsuario = "usuarioNuevo";
        String nuevaPass = "passValida123";

        when(this.vistaMock.getRegNombreReal()).thenReturn("Usuario Nuevo");
        when(this.vistaMock.getRegUsserName()).thenReturn(nuevoUsuario);
        when(this.vistaMock.getRegPassword()).thenReturn(nuevaPass);
        when(this.vistaMock.getRegConfirmPassword()).thenReturn(nuevaPass); 
        
        this.controladorBajoPrueba.registrar();
        verify(optionPaneMock, never()).ShowMessage(anyString());
        Cliente cliente= Empresa.getInstance().getClientes().get(nuevoUsuario);
        Assert.assertEquals(cliente.getNombreReal(),"Usuario Nuevo");
        Assert.assertEquals(cliente.getNombreUsuario(),nuevoUsuario);
        Assert.assertEquals(cliente.getPass(),nuevaPass);

    }
    
}

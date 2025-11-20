package controlador;


import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.event.ActionEvent;

import controlador.Controlador;
import vista.IVista;

public class controladorTestActionPerformed {
	private IVista vistaMock;
    private Controlador controladorReal;
    private Controlador controladorATestear;
    
    
    
	@Before
	public void setUp() throws Exception {
		// Preparamos los mocks y los controladores para testear
        this.vistaMock = Mockito.mock(IVista.class);
        this.controladorReal = new Controlador();
        this.controladorATestear = Mockito.spy(controladorReal);
        controladorATestear.setVista(vistaMock);        
	}
	
	
	
	//Tests
	
	@Test
    public void testActionPerformed1() {
        String comando = util.Constantes.CERRAR_SESION_CLIENTE;
        // Simulamos un evento con el comando correspondiente
        ActionEvent evento = new ActionEvent(this, 1, comando);
        doNothing().when(this.controladorATestear).logout();
        this.controladorATestear.actionPerformed(evento);
        
        // Verificamos que se llama el método
        verify(this.controladorATestear).logout();
    }
	@Test
    public void testActionPerformed2() {
        String comando = util.Constantes.CERRAR_SESION_ADMIN; 
        // Simulamos un evento con el comando correspondiente
        ActionEvent evento = new ActionEvent(this, 1, comando);
        doNothing().when(this.controladorATestear).logout();
        this.controladorATestear.actionPerformed(evento);
        
	    // Verificamos que se llama el método
        verify(this.controladorATestear).logout();
    }
	@Test
    public void testActionPerformed3() {
        // Simulamos un evento con el comando correspondiente
        String comandoLogin = util.Constantes.LOGIN; 
        ActionEvent evento = new ActionEvent(this, 1, comandoLogin);
        doNothing().when(this.controladorATestear).login();
        this.controladorATestear.actionPerformed(evento);

	     // Verificamos que se llama el método
	     verify(this.controladorATestear).login(); 
    }
	@Test
    public void testActionPerformed4() {
        // Simulamos un evento con el comando correspondiente
        String comando = util.Constantes.REG_BUTTON_REGISTRAR; 
        ActionEvent evento = new ActionEvent(this, 1, comando);
        doNothing().when(this.controladorATestear).registrar();
        this.controladorATestear.actionPerformed(evento);

	    // Verificamos que se llama el método
        verify(this.controladorATestear).registrar();
    }
	@Test
    public void testActionPerformed5() {
		// Simulamos un evento con el comando correspondiente
        String comando = util.Constantes.NUEVO_PEDIDO;
        ActionEvent evento = new ActionEvent(this, 1, comando);
        doNothing().when(this.controladorATestear).nuevoPedido();
        this.controladorATestear.actionPerformed(evento);

        // Verificamos que se llama el método
        verify(this.controladorATestear).nuevoPedido();
    }
	@Test
    public void testActionPerformed6() {
		// Simulamos un evento con el comando correspondiente
        String comando = util.Constantes.CALIFICAR_PAGAR; 
        ActionEvent evento = new ActionEvent(this, 1, comando);
        doNothing().when(this.controladorATestear).calificarPagar();
        this.controladorATestear.actionPerformed(evento);
        
        // Verificamos que se llama el método
        verify(this.controladorATestear).calificarPagar();
    }
	@Test
    public void testActionPerformed7() {
		// Simulamos un evento con el comando correspondiente
        String comando = util.Constantes.NUEVO_CHOFER;
        ActionEvent evento = new ActionEvent(this, 1, comando);        
        doNothing().when(this.controladorATestear).nuevoChofer();
        this.controladorATestear.actionPerformed(evento);

        // Verificamos que se llama el método
        verify(this.controladorATestear).nuevoChofer();
    }
	@Test
    public void testactionPerformed8() {
		// Simulamos un evento con el comando correspondiente
        String comando = util.Constantes.NUEVO_VEHICULO;
        ActionEvent evento = new ActionEvent(this, 1, comando);     
        doNothing().when(this.controladorATestear).nuevoVehiculo();
        this.controladorATestear.actionPerformed(evento);

        // Verificamos que se llama el método
        verify(this.controladorATestear).nuevoVehiculo();
    }
	@Test
    public void testActionPerformed9() {
		// Simulamos un evento con el comando correspondiente
        String comando = util.Constantes.NUEVO_VIAJE; 
        ActionEvent evento = new ActionEvent(this, 1, comando);
        doNothing().when(this.controladorATestear).nuevoViaje();
        this.controladorATestear.actionPerformed(evento);

        // Verificamos se llama el método
        verify(this.controladorATestear).nuevoViaje();
    }
	@Test
    public void testActionPerformed10() {
		// Simulamos un evento con un comando inexistente
        String comando = "TallerProgramacion";
        ActionEvent evento = new ActionEvent(this, 1, comando);
        this.controladorATestear.actionPerformed(evento);

        // Verificamos que NUNCA se llamaron estos métodos
        verify(this.controladorATestear, never()).login();
        verify(this.controladorATestear, never()).logout();
        verify(this.controladorATestear, never()).registrar();
        verify(this.controladorATestear, never()).nuevoPedido();
        verify(this.controladorATestear, never()).calificarPagar();
        verify(this.controladorATestear, never()).nuevoChofer();
        verify(this.controladorATestear, never()).nuevoVehiculo();
        verify(this.controladorATestear, never()).nuevoViaje();
    }
	
}

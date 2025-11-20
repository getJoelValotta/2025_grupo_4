package controlador;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controlador.Controlador;
import excepciones.VehiculoRepetidoException;
import junit.framework.Assert;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

@SuppressWarnings("deprecation")
public class controladorTestCalificaPagarNuevoPedidoViaje {
	private Controlador controladorReal;
	private Controlador controladorBajoPrueba; // El "spy"
	private IVista vistaMock; // El único mock que necesitamos
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
        Empresa.getInstance().setChoferes(new HashMap<>());
        Empresa.getInstance().setPedidos(new HashMap<>());
        Empresa.getInstance().setChoferesDesocupados(new ArrayList<>());
        Empresa.getInstance().setVehiculos(new HashMap<>());
        Empresa.getInstance().setVehiculosDesocupados(new ArrayList<>());
        Empresa.getInstance().setViajesIniciados(new HashMap<>());
        Empresa.getInstance().setViajesTerminados(new ArrayList<>());
	}
	private void cargarEscenarioCompleto() {
        
		// --- 1. Definir Recursos ---
		Chofer chofer1 = null;
		Vehiculo auto1 = null;
		Cliente clientePedido = null;
		Cliente clienteViaje = null;
		
		// --- 2. Agregar Clientes ---
		try {
			Empresa.getInstance().agregarCliente("clientePedido", "pass", "Cliente Con Pedido");
			Empresa.getInstance().agregarCliente("clienteViaje", "pass", "Cliente Con Viaje");
			Empresa.getInstance().agregarCliente("clienteLibre", "pass", "Cliente Libre");
		}
		catch(Exception e) {
		}
		// --- 3. Agregar Choferes ---
		try {
			chofer1 = new ChoferTemporario("111", "Chofer Temp");
			Chofer chofer2 = new ChoferPermanente("222", "Chofer Perm", 2010, 0);
		    Empresa.getInstance().agregarChofer(chofer1);
		    Empresa.getInstance().agregarChofer(chofer2);
		}
		catch(Exception e) {
		}
		// --- 4. Agregar Vehículos ---
		try {
			auto1 = new Auto("AUTO123", 4, false); // Auto común
			Auto auto2 = new Auto("AUTO234", 4, true); // Auto común
			Vehiculo moto = new Moto("MOTO123");
		    Empresa.getInstance().agregarVehiculo(auto1);
		    Empresa.getInstance().agregarVehiculo(auto2);
		    Empresa.getInstance().agregarVehiculo(moto);
		}
		catch(Exception e) {
		}
		// --- 5. Crear "Cliente con Pedido" ---
		try {
			clientePedido = (Cliente) Empresa.getInstance().login("clientePedido", "pass");		
			// Creamos un Pedido
			// Pedido(Cliente c, int pax, boolean mascota, boolean baul, int km,string zona)
			Pedido pedido = new Pedido(clientePedido, 2, false, true, 10,Constantes.ZONA_STANDARD); 

			Empresa.getInstance().agregarPedido(pedido);
			
		}
		catch(Exception e) {
		}
		// --- 6. Crear "Cliente con Viaje" ---
		try {
		clienteViaje = (Cliente) Empresa.getInstance().login("clienteViaje", "pass");
		// Creamos un Pedido y un viaje en curso
		Pedido pedidoViaje = new Pedido(clienteViaje, 1, false, false, 5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedidoViaje);

		Empresa.getInstance().logout();
		Empresa.getInstance().login("admin", "admin");
		Empresa.getInstance().crearViaje(pedidoViaje, chofer1, auto1);
		
		Empresa.getInstance().logout();
		}
		catch(Exception e) {
		}
	}
	private void cargarEscenarioSinPedidos() {
        
		// --- 1. Definir Recursos ---
		Chofer chofer1 = null;
		Vehiculo auto1 = null;
		
		// --- 2. Agregar Cliente ---
		try {
			Empresa.getInstance().agregarCliente("clienteLibre", "pass", "Cliente Libre");
		}
		catch(Exception e) {
		}
		// --- 3. Agregar Chofer ---
		try {
			chofer1 = new ChoferPermanente("222", "Chofer Perm", 2010, 0);
		    Empresa.getInstance().agregarChofer(chofer1);
		}
		catch(Exception e) {
		}
		// --- 4. Agregar Vehículo ---
		try {
			auto1 = new Auto("AUTO123", 4, true); // Auto común
		    Empresa.getInstance().agregarVehiculo(auto1);
		}
		catch(Exception e) {
		}
	}
	// calificarPagar()
	@Test
    public void testCalificarPagar1() {
        // Cargamos escenario con cliente con viaje en curso
        cargarEscenarioCompleto();
        try {
            Empresa.getInstance().login("clienteViaje", "pass");
        } catch (Exception e) {
            fail("Login del cliente incorrecto. " + e.getMessage());
        }
        
        // vista.getCalificacion() = 5
        when(this.vistaMock.getCalificacion()).thenReturn(5);
        
        this.controladorBajoPrueba.calificarPagar();
        verify(this.vistaMock).getCalificacion();
        Viaje viaje=Empresa.getInstance().getViajesTerminados().get(0);
        Assert.assertEquals(viaje.getCalificacion(),5);
        // Verificamos que se actualiza la vista
        verify(this.vistaMock).actualizar(); 

        // Verificamos que no se mostró mensaje de error
        verify(optionPaneMock, never()).ShowMessage(anyString());
        assertEquals("El viaje no se finalizó", 0, Empresa.getInstance().getViajesIniciados().size());
    }
	
    @Test
    public void testCalificarPagar2() {
                
        // Cargamos escenario con cliente sin viaje en curso
        cargarEscenarioCompleto();
        try {
            Empresa.getInstance().login("clienteLibre", "pass");
        } catch (Exception e) {
            fail("Login del cliente incorrecto. " + e.getMessage());
        }

        // vista.getCalificacion() = 5
        when(this.vistaMock.getCalificacion()).thenReturn(5);
                
        this.controladorBajoPrueba.calificarPagar();
        // Verificamos que si se muestra mensaje de error
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor());
        //Verificamos que NO se actualiza la vista
        verify(this.vistaMock, never()).actualizar();
    }
    
	//NUEVO PEDIDO
    @Test
    public void testNuevoPedido1() {
        cargarEscenarioSinPedidos();
        
        String usuario = "clienteLibre";
        String pass = "pass";
        
        try {
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("Falló la preparación del test (login): " + e.getMessage());
        }
        when(this.vistaMock.getCantidadPax()).thenReturn(2);
        when(this.vistaMock.isPedidoConMascota()).thenReturn(true);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(8);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        this.controladorBajoPrueba.nuevoPedido();
        Cliente cliente= Empresa.getInstance().getClientes().get(usuario);
        Pedido pedido=Empresa.getInstance().getPedidoDeCliente(cliente);
        Assert.assertEquals(2,pedido.getCantidadPasajeros());
        Assert.assertEquals(true,pedido.isMascota());
        Assert.assertEquals(false,pedido.isBaul());
        Assert.assertEquals(8,pedido.getKm());
        Assert.assertEquals(Constantes.ZONA_STANDARD,pedido.getZona());
        
        
        verify(this.vistaMock).actualizar(); 
        verify(optionPaneMock, never()).ShowMessage(anyString());
        assertEquals(1, Empresa.getInstance().getPedidos().size());
    }
    @Test
    public void testNuevoPedido2() {
        cargarEscenarioCompleto();
        
        String usuario = "clientePedido";
        String pass = "pass";
        
        try {
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("Falló la preparación del test (login): " + e.getMessage());
        }
        
        when(this.vistaMock.getCantidadPax()).thenReturn(2);
        when(this.vistaMock.isPedidoConMascota()).thenReturn(true);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(10);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        
        this.controladorBajoPrueba.nuevoPedido();
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor());
        assertEquals(1, Empresa.getInstance().getPedidos().size());
    }
    @Test
    public void testNuevoPedido3() {
        cargarEscenarioCompleto();
        
        String usuario = "clienteLibre";
        String pass = "pass";
        
        try {
            Empresa.getInstance().login(usuario, pass);
        } catch (Exception e) {
            fail("Falló la preparación del test (login): " + e.getMessage());
        }
        when(this.vistaMock.isPedidoConMascota()).thenReturn(false);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(10);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        when(this.vistaMock.getCantidadPax()).thenReturn(5);
        this.controladorBajoPrueba.nuevoPedido();
        
        verify(optionPaneMock).ShowMessage(Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor());
        
        assertEquals(1, Empresa.getInstance().getPedidos().size());
    }

    @Test
    public void testNuevoPedido4() {
        cargarEscenarioCompleto();

        try {
            Empresa.getInstance().login("clienteViaje", "pass");
        } catch (Exception e) {
            fail("Falló el login del 'clienteViaje': " + e.getMessage());
        }
        

        when(this.vistaMock.getCantidadPax()).thenReturn(1);
        when(this.vistaMock.isPedidoConMascota()).thenReturn(false);
        when(this.vistaMock.isPedidoConBaul()).thenReturn(false);
        when(this.vistaMock.getCantKm()).thenReturn(5);
        when(this.vistaMock.getTipoZona()).thenReturn(Constantes.ZONA_STANDARD);
        this.controladorBajoPrueba.nuevoPedido();
       
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor());

        verify(this.vistaMock, never()).actualizar();
    }
    private void cargarEscenarioConUnChofer() {
        Chofer chofer = null;
		Vehiculo auto = null;
		try {
			Empresa.getInstance().agregarCliente("clientePedido", "pass", "Cliente Pedido");		
			chofer = new ChoferPermanente("111", "Chofer Perm", 2010, 0);
			auto = new Auto("AUTO123", 4, false); // Auto común
		    Empresa.getInstance().agregarChofer(chofer);
		    Empresa.getInstance().agregarVehiculo(auto);
			Cliente clientePedido = (Cliente) Empresa.getInstance().login("clientePedido", "pass");		
		    Empresa.getInstance().setUsuarioLogeado(clientePedido);
		    
			Pedido pedido = new Pedido(clientePedido, 2, false, true, 10,Constantes.ZONA_STANDARD); 
			Empresa.getInstance().agregarPedido(pedido);
		}
		catch(Exception e) {
		}
	}
    @Test
    public void testNuevoViaje1() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        Chofer chofer = Empresa.getInstance().getChoferes().get("111"); 
        ArrayList<Vehiculo> lista = Empresa.getInstance().getVehiculosDesocupados();
        Vehiculo vehiculo = lista.get(0);
        

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        
        this.controladorBajoPrueba.nuevoViaje();

        assertEquals("El viaje no se creó", 1, Empresa.getInstance().getViajesIniciados().size());

        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());
        
        assertEquals("El chofer no coincide",chofer,Empresa.getInstance().getViajesIniciados().get(cliente).getChofer());
        assertEquals("El vehiculo no coincide",vehiculo,Empresa.getInstance().getViajesIniciados().get(cliente).getVehiculo());

    }
    @Test
    public void testNuevoViaje2() {
        cargarEscenarioConUnChofer();
        Chofer chofer = Empresa.getInstance().getChoferes().get("111");
        Vehiculo vehiculo = Empresa.getInstance().getVehiculosDesocupados().get(0);
        
        Pedido pedidoFalso = Mockito.mock(Pedido.class);

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedidoFalso);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        
        this.controladorBajoPrueba.nuevoViaje();

    
        verify(optionPaneMock).ShowMessage(Mensajes.PEDIDO_INEXISTENTE.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
    }
    @Test
    public void testNuevoViaje3() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        Vehiculo vehiculo = Empresa.getInstance().getVehiculosDesocupados().get(0);
        Chofer chofer=Empresa.getInstance().getChoferes().get("111");
        //No hay choferes Disponibles
        Empresa.getInstance().setChoferesDesocupados(new ArrayList<>());        
        
        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo);
        
        this.controladorBajoPrueba.nuevoViaje();

        verify(optionPaneMock).ShowMessage(Mensajes.CHOFER_NO_DISPONIBLE.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
   
    }
    @Test
    public void testNuevoViaje4() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        Chofer chofer = Empresa.getInstance().getChoferes().get("111");
        
        Vehiculo vehiculoFalso = new Auto("FALSO123", 4, false);

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculoFalso);
        
        this.controladorBajoPrueba.nuevoViaje();

        verify(optionPaneMock).ShowMessage(Mensajes.VEHICULO_NO_DISPONIBLE.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
    }
    @Test
    public void testNuevoViaje5() {
        cargarEscenarioConUnChofer(); 
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido = Empresa.getInstance().getPedidoDeCliente(cliente);
        
        Chofer chofer = Empresa.getInstance().getChoferes().get("111");
        
        Empresa.getInstance().setVehiculosDesocupados(new ArrayList<>());        
        Vehiculo vehiculoNoCumple = new Auto("NOCUMPLE123", 1, true);
    	try {
			Empresa.getInstance().agregarVehiculo(vehiculoNoCumple);
		} catch (VehiculoRepetidoException e) {
			fail();
		}

        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculoNoCumple);
        
        this.controladorBajoPrueba.nuevoViaje();
        verify(optionPaneMock).ShowMessage(Mensajes.VEHICULO_NO_VALIDO.getValor());

        verify(this.vistaMock, never()).actualizar();

        assertEquals("Se creó un viaje incorrectamente",0,Empresa.getInstance().getViajesIniciados().size());
    }
    @Test
    public void testNuevoViaje6() {
        cargarEscenarioConUnChofer();
        Cliente cliente = Empresa.getInstance().getClientes().get("clientePedido");
        Pedido pedido1 = Empresa.getInstance().getPedidoDeCliente(cliente);
        Chofer chofer1 = Empresa.getInstance().getChoferes().get("111");
        Vehiculo vehiculo1 = Empresa.getInstance().getVehiculosDesocupados().get(0);
        try {
        Empresa.getInstance().login("clientePedido", "pass");
        Empresa.getInstance().crearViaje(pedido1, chofer1, vehiculo1);
        }
        catch(Exception e) {
        	fail();
        }
        Pedido pedido2=new Pedido(cliente, 1, false, false, 1, Constantes.ZONA_STANDARD);
        Chofer chofer2=new ChoferTemporario("456", "chofer2");
        Vehiculo vehiculo2 = new Auto("4567", 1, true);
        HashMap<Cliente, Pedido> nuevosPedidos = new HashMap<>();        
        nuevosPedidos.put(cliente, pedido2);
        Empresa.getInstance().setPedidos(nuevosPedidos);        
        when(this.vistaMock.getPedidoSeleccionado()).thenReturn(pedido2);
        when(this.vistaMock.getChoferDisponibleSeleccionado()).thenReturn(chofer2);
        when(this.vistaMock.getVehiculoDisponibleSeleccionado()).thenReturn(vehiculo2);
        this.controladorBajoPrueba.nuevoViaje();
        assertEquals("Se creó un segundo viaje incorrectamente", 1,Empresa.getInstance().getViajesIniciados().size());
        verify(optionPaneMock).ShowMessage(Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor());
        verify(this.vistaMock, never()).actualizar();
   
    }
}

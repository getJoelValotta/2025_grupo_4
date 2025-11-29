package controladorTest;

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
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Mensajes;
import vista.IOptionPane;
import vista.IVista;

public class controladorTestNuevoChoferVehiculo {

	private IVista vistaMock;
	private IOptionPane optionPaneMock;
	private Controlador controladorReal;
	private Controlador controladorBajoPrueba;
	@Before
	public void setUp() throws Exception {
        this.vistaMock = Mockito.mock(IVista.class);
        this.optionPaneMock = Mockito.mock(IOptionPane.class);
        this.controladorReal = new Controlador();
        this.controladorReal.setVista(this.vistaMock);
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

	@Test
    public void testNuevoChofer1() {
        
        String dni = "45539109";
        String nombre = "Juancito Temp";
        when(this.vistaMock.getTipoChofer()).thenReturn("TEMPORARIO");
        when(this.vistaMock.getNombreChofer()).thenReturn(nombre);
        when(this.vistaMock.getDNIChofer()).thenReturn(dni);
        
        this.controladorBajoPrueba.nuevoChofer();
        verify(this.vistaMock).actualizar(); 
        verify(optionPaneMock, never()).ShowMessage(anyString());
        Chofer choferAgregado = Empresa.getInstance().getChoferes().get(dni);
        assertNotNull("El chofer no se agregó a la empresa", choferAgregado);
        assertTrue("El chofer no es de tipo Temporario", choferAgregado instanceof ChoferTemporario);
        assertEquals("El nombre del chofer es incorrecto", nombre, choferAgregado.getNombre());
        assertEquals("El DNI del chofer es incorrecto", dni, choferAgregado.getDni());
    }
	@Test
    public void testNuevoChofer2() {
        String dni = "42828348";
        String nombre = "Nahue permanente";
        when(this.vistaMock.getTipoChofer()).thenReturn("PERMANENTE");
        when(this.vistaMock.getNombreChofer()).thenReturn(nombre);
        when(this.vistaMock.getDNIChofer()).thenReturn(dni);
        when(this.vistaMock.getAnioChofer()).thenReturn(2012);
        when(this.vistaMock.getHijosChofer()).thenReturn(1);  
        this.controladorBajoPrueba.nuevoChofer();  
        
        // Verificamos que se actualice la vista
        verify(this.vistaMock).actualizar(); 
        
        // Verificamos que NO hubo error
        verify(optionPaneMock, never()).ShowMessage(anyString());
        
        // Verificamos que el chofer se agregó Y es Permanente
        Chofer choferAgregado = Empresa.getInstance().getChoferes().get(dni);
        
        assertNotNull("El chofer no se agregó", choferAgregado);
        assertTrue("El chofer no es permanente", choferAgregado instanceof ChoferPermanente);
        assertEquals("El nombre del chofer es incorrecto", nombre, choferAgregado.getNombre());
        assertEquals("El DNI del chofer es incorrecto", dni, choferAgregado.getDni());
        assertEquals("La cantidad de hijos es incorrecta", 1, ((ChoferPermanente) choferAgregado).getCantidadHijos());
        assertEquals("El anio de ingreso es incorrecto",2012,((ChoferPermanente) choferAgregado).getAnioIngreso());
    }
	@Test
    public void testNuevoChofer3() {
        
        String dniRepetido = "202020";
        
        try {
            Empresa.getInstance().agregarChofer(new ChoferTemporario(dniRepetido, "Primer Chofer"));
        } catch (Exception e) {
            fail("Falla en el setup: " + e.getMessage());
        }

        when(this.vistaMock.getTipoChofer()).thenReturn("TEMPORARIO");
        when(this.vistaMock.getNombreChofer()).thenReturn("Segundo Chofer");
        when(this.vistaMock.getDNIChofer()).thenReturn(dniRepetido);
        
        // ChoferRepetidoException
        this.controladorBajoPrueba.nuevoChofer();

        verify(optionPaneMock).ShowMessage(Mensajes.CHOFER_YA_REGISTRADO.getValor());

        verify(this.vistaMock, never()).actualizar();
    }
	
	@Test
    public void testNuevoVehiculo1() {
        String patente = "MOTO666";
        
        when(this.vistaMock.getTipoVehiculo()).thenReturn("MOTO");
        when(this.vistaMock.getPatente()).thenReturn(patente);
        
        this.controladorBajoPrueba.nuevoVehiculo();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Vehiculo vehiculoAgregado = Empresa.getInstance().getVehiculos().get(patente);
        assertNotNull("El vehículo no se agregó", vehiculoAgregado);
        assertTrue("El vehículo no es de tipo Moto", vehiculoAgregado instanceof Moto);
        assertEquals("La patente no coincide",patente,vehiculoAgregado.getPatente());
	}
	@Test
    public void testNuevoVehiculo2() {
        String patente = "AUTO000";
        int plazas=4;
        boolean mascotas=true;
        when(this.vistaMock.getTipoVehiculo()).thenReturn("AUTO");
        when(this.vistaMock.getPatente()).thenReturn(patente);
        when(this.vistaMock.getPlazas()).thenReturn(plazas);
        when(this.vistaMock.isVehiculoAptoMascota()).thenReturn(mascotas);
        
        this.controladorBajoPrueba.nuevoVehiculo();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Vehiculo vehiculoAgregado = Empresa.getInstance().getVehiculos().get(patente);
	    assertNotNull("El vehículo no se agregó", vehiculoAgregado);
	    assertTrue("El vehículo no es de tipo Auto", vehiculoAgregado instanceof Auto);
        assertEquals("La patente no coincide",patente,vehiculoAgregado.getPatente());
	    assertEquals("Las plazas son incorrectas", plazas, ((Auto) vehiculoAgregado).getCantidadPlazas());
	    assertEquals("La condicion de mascotas no coincide",mascotas,((Auto) vehiculoAgregado).isMascota());
	}
	@Test
    public void testNuevoVehiculo3() {
        String patente = "COMBI333";
        int plazas=7;
        boolean mascotas=true;
        when(this.vistaMock.getTipoVehiculo()).thenReturn("COMBI");
        when(this.vistaMock.getPatente()).thenReturn(patente);
        when(this.vistaMock.getPlazas()).thenReturn(plazas);
        when(this.vistaMock.isVehiculoAptoMascota()).thenReturn(mascotas);
        
        this.controladorBajoPrueba.nuevoVehiculo();
        
        verify(this.vistaMock).actualizar(); 

        verify(optionPaneMock, never()).ShowMessage(anyString());

        Vehiculo vehiculoAgregado = Empresa.getInstance().getVehiculos().get(patente);
	    assertNotNull("El vehículo no se agregó", vehiculoAgregado);
	    assertTrue("El vehículo no es de tipo Auto", vehiculoAgregado instanceof Combi);
        assertEquals("La patente no coincide",patente,vehiculoAgregado.getPatente());
	    assertEquals("Las plazas son incorrectas", plazas, ((Combi) vehiculoAgregado).getCantidadPlazas());
	    assertEquals("La condicion de mascotas no coincide",mascotas,((Combi) vehiculoAgregado).isMascota());
	}
	@Test
    public void testNuevoVehiculo4() {
        String patenteRepetida = "REP123";
        
        try {
            Empresa.getInstance().agregarVehiculo(new Moto(patenteRepetida));
        } catch (Exception e) {
            fail("Falla en el setup: " + e.getMessage());
        }

        when(this.vistaMock.getTipoVehiculo()).thenReturn("MOTO");
        when(this.vistaMock.getPatente()).thenReturn(patenteRepetida);
        
        // VehiculoYaRegistradoException
        this.controladorBajoPrueba.nuevoVehiculo();

        verify(optionPaneMock).ShowMessage(Mensajes.VEHICULO_YA_REGISTRADO.getValor());
        
        verify(this.vistaMock, never()).actualizar();
    }

}

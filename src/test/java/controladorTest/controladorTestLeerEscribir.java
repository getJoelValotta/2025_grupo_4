package controlador;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controlador.Controlador;
import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import persistencia.IPersistencia;
import vista.IOptionPane;
import vista.IVista;

public class controladorTestLeerEscribir {


	private IVista vistaMock;
	private IOptionPane optionPaneMock;
	private IPersistencia<EmpresaDTO> persistenciaMock;
	private EmpresaDTO dtoMock;
	private Controlador controladorReal;
	private Controlador controladorBajoPrueba;

	@SuppressWarnings("unchecked")
	@Before
    public void setUp() throws Exception {
        this.vistaMock = Mockito.mock(IVista.class);
        this.optionPaneMock = Mockito.mock(IOptionPane.class);
        
        this.persistenciaMock = (IPersistencia<EmpresaDTO>) Mockito.mock(IPersistencia.class);
        this.dtoMock = Mockito.mock(EmpresaDTO.class); // Mock de DTO vacío

        this.controladorReal = new Controlador();
        
        this.controladorReal.setVista((IVista) this.vistaMock);
        this.controladorReal.setPersistencia(this.persistenciaMock);

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
    public void testLeer1() {
        try {
            when(this.persistenciaMock.leer()).thenReturn(this.dtoMock);
        } catch (Exception e) {
            fail("Falla en setup del mock. " + e.getMessage());
        }
        doNothing().when(this.controladorBajoPrueba).escribir();
        this.controladorBajoPrueba.leer();
        try {
            verify(this.persistenciaMock).leer();
        } catch (Exception e) {
            fail("Falla en ve: " + e.getMessage());
        }
        // verificamos que no se muestre mensaje por ventana
        verify(optionPaneMock, never()).ShowMessage(anyString());
        
        // verificamos que NO se escriba un nuevo archivo
        verify(this.controladorBajoPrueba, never()).escribir();
    }
	@Test
    public void testLeer2() {
        
        try {
            when(this.persistenciaMock.leer())
                .thenThrow(new IOException("Archivo corrupto"));
        } catch (Exception e) {
            fail("Falla en setup del mock. " + e.getMessage());
        }

        doNothing().when(this.controladorBajoPrueba).escribir();

        this.controladorBajoPrueba.leer();

        try {
            verify(this.persistenciaMock).leer();
        } catch (Exception e) {
            fail("Falla en verify del mock (leer): " + e.getMessage());
        }

        // verificamos que se muestra mensaje por ventana
        verify(optionPaneMock).ShowMessage(anyString()); 

        // verificamos que escribe archivo nuevo
        verify(this.controladorBajoPrueba).escribir();
    }

    @Test
    public void testEscribir1() {
        try {
            doNothing().when(this.persistenciaMock).escribir(any());
        } catch (Exception e) {
            fail("Falla en setup del mock (escribir): "+ e.getMessage());
        }

        this.controladorBajoPrueba.escribir();

        // Verificamos que se llamó a persistencia.escribir()
        try {
            verify(this.persistenciaMock, times(1)).escribir(any());
        } catch (Exception e) {
            fail("Falla en verify del mock (escribir): " + e.getMessage());
        }
            
        // Verificamos que NO se mostró ningún error
        verify(optionPaneMock, never()).ShowMessage(anyString());
    }
    @Test
    public void testEscribir2() {

        try {
            doThrow(new IOException("Simulación: Permiso denegado")).when(this.persistenciaMock).escribir(any());
        } catch (Exception e) {
            fail("Falla en setup del mock (escribir): " + e.getMessage());
        }

        this.controladorBajoPrueba.escribir();

        //Verificamos que se intentó escribir
        try {
            verify(this.persistenciaMock, times(1)).escribir(any());
        } catch (Exception e) {
            fail("Falla en verify del mock (escribir): " + e.getMessage());
        }

        verify(optionPaneMock).ShowMessage(anyString()); 
    }
}

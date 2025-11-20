package persistencia;

import modeloDatos.*;
import org.junit.*;
import modeloNegocio.Empresa;
import excepciones.*;
import persistencia.EmpresaDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenciaBINVaciaTest
{
    public EmpresaDTO empresaDTO;
    public Empresa empresa;
    public ArrayList<Pedido> pedidosTest;
    public ArrayList<Cliente> clientesTest;
    public ArrayList<Vehiculo> vehiculosTest;
    public ArrayList<Chofer> choferesTest;
    public ArrayList<Viaje> viajesIniciadosTest;
    public ArrayList<Viaje> viajesTerminadosTest;

    public void setUp() {
        empresa = Empresa.getInstance();
        clientesTest = new ArrayList<Cliente>();
        pedidosTest = new ArrayList<Pedido>();
        vehiculosTest = new ArrayList<Vehiculo>();
        choferesTest = new ArrayList<Chofer>();
        viajesTerminadosTest = new ArrayList<Viaje>();
        viajesIniciadosTest = new ArrayList<Viaje>();
        empresaDTO = new EmpresaDTO();
    }

    public void tearDown() {
        empresa.setChoferes(new HashMap<String,Chofer>());
        empresa.setChoferesDesocupados(new ArrayList<Chofer>());
        empresa.setClientes(new HashMap<String, Cliente>());
        empresa.setPedidos(new HashMap<Cliente, Pedido>());
        empresa.setUsuarioLogeado(null);
        empresa.setVehiculos(new HashMap<String, Vehiculo>());
        empresa.setVehiculosDesocupados(new ArrayList<Vehiculo>());
        empresa.setViajesIniciados(new HashMap<Cliente, Viaje>());
        empresa.setViajesTerminados(new ArrayList<Viaje>());
        empresa = null;
        empresaDTO = null;
        File archivo = new File("persis_test.bin");
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @Test
    public void PersistenciaEmpresaTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresa = (EmpresaDTO) persistencia.leer();
            persistencia.cerrarInput();
            Assert.assertEquals("Fallaron los Choferes", empresaDTO.getChoferes(), empresa.getChoferes());
            Assert.assertEquals("Fallaron los Clientes", empresaDTO.getClientes(), empresa.getClientes());
            Assert.assertEquals("Fallaron los Choferes Desocupados", empresaDTO.getChoferesDesocupados(), empresa.getChoferesDesocupados());
            Assert.assertEquals("Fallaron los Pedidos", empresaDTO.getPedidos(), empresa.getPedidos());
            Assert.assertEquals("Fallaron los Usuarios Loggueados", empresaDTO.getUsuarioLogeado(), empresa.getUsuarioLogeado());
            Assert.assertEquals("Fallaron los Vehiculos", empresaDTO.getVehiculos().entrySet(), empresa.getVehiculos().entrySet());
            Assert.assertEquals("Fallaron los Vehiculos Desocupados", empresaDTO.getVehiculosDesocupados(), empresa.getVehiculosDesocupados());
            Assert.assertEquals("Fallaron los Viajes Iniciados", empresaDTO.getViajesIniciados(), empresa.getViajesIniciados());
            Assert.assertEquals("Fallaron los Viajes Terminados", empresaDTO.getViajesTerminados(), empresa.getViajesTerminados());
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

}

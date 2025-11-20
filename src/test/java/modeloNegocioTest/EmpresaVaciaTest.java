package modeloNegocioTest;

import modeloDatos.*;
import org.junit.*;
import util.Constantes;
import modeloNegocio.Empresa;
import excepciones.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class EmpresaVaciaTest {
    Empresa empresa;

    @Before
    public void setUp() throws Exception {
        empresa = Empresa.getInstance();
    }

    //Caso particular de los singleton: Se necesita este teardown luego de ejecutar cada test
    //Para probar los casos vacios. Esto simplemente "borra el lienzo" en cada iteracion de test.
    @After
    public void tearDown() throws Exception {
        empresa.setClientes(new HashMap<String, Cliente>());
        empresa.setChoferesDesocupados(new ArrayList<Chofer>());
        empresa.setChoferes(new HashMap<String, Chofer>());
        empresa.setVehiculos(new HashMap<String, Vehiculo>());
        empresa.setVehiculosDesocupados(new ArrayList<Vehiculo>());
        empresa.setPedidos(new HashMap<Cliente, Pedido>());
        empresa.setViajesIniciados(new HashMap<Cliente, Viaje>());
        empresa.setViajesTerminados(new ArrayList<Viaje>());
        empresa.setUsuarioLogeado(null);
    }


    @Test
    public void getInstanceTest()
    {
        assertEquals("Deberia ser la misma instancia", empresa, Empresa.getInstance());
    }

    @Test
    public void getClientesTest()
    {
        if(!empresa.getClientes().isEmpty())
            fail("El HashMap de clientes deberia estar vacio");
    }

    @Test
    public void getChoferesDesocupadosTest()
    {
        if(!empresa.getChoferesDesocupados().isEmpty())
            fail("El ArrayList de choferes desocupados deberia estar vacio");
    }

    @Test
    public void getChoferesTest()
    {
        if(!empresa.getChoferes().isEmpty())
            fail("El ArrayList de choferes deberia estar vacio");
    }

    @Test
    public void getVehiculosTest()
    {
        if(!empresa.getVehiculos().isEmpty())
            fail("El HashMap de vehiculos deberia estar vacio");
    }

    @Test
    public void getVehiculosDesocupadosTest()
    {
        if(!empresa.getVehiculosDesocupados().isEmpty())
            fail("El HashMap de vehiculos desocupados deberia estar vacio");
    }

    @Test
    public void getPedidosTest()
    {
        if(!empresa.getPedidos().isEmpty())
            fail("El HashMap de pedidos deberia estar vacio");
    }

    @Test
    public void getViajesIniciadosTest()
    {
        if(!empresa.getViajesIniciados().isEmpty())
            fail("El HashMap de viajes iniciados deberia estar vacio");
    }

    @Test
    public void getViajesTerminadosTest()
    {
        if(!empresa.getViajesTerminados().isEmpty())
            fail("El HashMap de viajes terminados deberia estar vacio");
    }

    @Test
    public void agregarChoferTest()
    {
        Chofer nuevo_chofer = new ChoferPermanente("555555555", "Montini Pablo", 2025, 2);
        try
        {
            empresa.agregarChofer(nuevo_chofer);
            if(!empresa.getChoferes().containsKey("555555555"))
                fail("Se deberia haber encontrado al chofer");
        }
        catch (ChoferRepetidoException e)
        {
            fail("No deberia estar repetido el chofer");
        }
    }

    @Test
    public void agregarClienteTest()
    {
        try
        {
            empresa.agregarCliente("Cualquiera", "123", "Montini Pablo");
            if(!empresa.getClientes().containsKey("Cualquiera"))
                fail("No se encontro al cliente");
        }
        catch (UsuarioYaExisteException e)
        {
            fail("No deberia estar repetido el cliente");
        }
    }

    @Test
    public void vehiculosOrdenadosPorPedidoTest()
    {
        Cliente cliente = new Cliente("Cualquiera", "123", "Montini Pablo");
        Pedido pedido = new Pedido(cliente, 8, true, false, 10, Constantes.ZONA_STANDARD);
        if(!empresa.vehiculosOrdenadosPorPedido(pedido).isEmpty())
            fail("Deberia haber devuelto un ArrayList vacio");
    }

    @Test
    public void validarPedidoTest()
    {
        Cliente cliente = new Cliente("Cualquiera", "123", "Montini Pablo");
        Pedido pedido = new Pedido(cliente, 8, true, false, 10, Constantes.ZONA_STANDARD);
        if(empresa.validarPedido(pedido))
            fail("No deberia haber entrado al if");
    }

    @Test
    public void agregarVehiculoTest()
    {
        Auto auto = new Auto("77VEG77", 4, false);
        try
        {
            empresa.agregarVehiculo(auto);
            if(!empresa.getVehiculos().containsKey("77VEG77"))
                fail("Deberia haberse agregado el vehiculo");
        }
        catch (VehiculoRepetidoException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void test_login()
    {
        try
        {
            empresa.login("Cualquiera", "123");
            fail("Se deberia haber lanzado una excepcion");
        }
        catch (UsuarioNoExisteException e)
        {
        }
        catch (PasswordErroneaException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
    }

    @Test
    public void getHistorialViajeClienteTest()
    {
        Cliente cliente = new Cliente("Cualquiera", "123", "Montini Pablo");
        if(!empresa.getHistorialViajeCliente(cliente).isEmpty())
            fail("Deberia haber devuelto un ArrayList vacio");
    }

    @Test
    public void getHistorialViajeChoferTest()
    {
        Chofer chofer = new ChoferPermanente("55555555", "Montini Pablo", 2025, 2);
        if(!empresa.getHistorialViajeChofer(chofer).isEmpty())
            fail("No devolvio el ArrayList vacio");
    }

    @Test
    public void calificacionDeChoferTest()
    {
        Chofer chofer = new ChoferPermanente("555555555", "Montini Pablo", 2025, 2);
        try
        {
            empresa.calificacionDeChofer(chofer);
            fail("SinViajesException no se ha lanzado");
        }
        catch (SinViajesException e)
        {
        }
    }

    @Test
    public void getPedidoDeClienteTest()
    {
        Cliente cliente = new Cliente("Facu889", "123", "Facundo Arregui");
        assertEquals("Daberian ser null", empresa.getPedidoDeCliente(cliente), null);
    }

    @Test
    public void getViajeDeClienteTest()
    {
        Cliente cliente = new Cliente("Facu889", "123", "Facundo Arregui");
        assertEquals("Daberian ser null", empresa.getViajeDeCliente(cliente), null);
    }

    @Test
    public void getUsuarioLogeadoTest()
    {
        assertEquals("Daberian ser null", empresa.getUsuarioLogeado(), null);
    }

    @Test
    public void isAdminTest()
    {
        assertEquals("Deberian dar false", empresa.isAdmin(), false);
    }
}


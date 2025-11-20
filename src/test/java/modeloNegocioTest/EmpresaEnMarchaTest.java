package modeloNegocioTest;

import modeloDatos.*;
import org.junit.*;
import util.Constantes;
import modeloNegocio.Empresa;
import excepciones.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class EmpresaEnMarchaTest {
    Empresa empresa;

    @Before
    public void setUp()
    {
        empresa = Empresa.getInstance();
        Chofer chofer_1 = new ChoferPermanente("777777777", "Jojo Valotta", 2022, 2);
        chofer_1.setSueldoBasico(100);
        Chofer chofer_2 = new ChoferPermanente("999999999", "Nahue Valds", 2010, 2);
        chofer_2.setSueldoBasico(100);
        empresa.getChoferes().put("777777777", chofer_1);
        empresa.getChoferes().put("999999999", chofer_2);

        Cliente cliente1 = new Cliente("Jojo", "123", "Jojo Valotta");
        Cliente cliente2 = new Cliente("Favio", "123", "Favio Mili");
        Cliente cliente3 = new Cliente("Lehuan20", "123", "Nahuel Valds");
        Cliente cliente4 = new Cliente("LoloProd", "123", "Lorenzo aka (el) Papa");
        Cliente cliente5 = new Cliente("Annaul", "123", "Easter egg");
        empresa.getClientes().put("Jojo", cliente1);
        empresa.getClientes().put("Favio", cliente2);
        empresa.getClientes().put("Lehuan20", cliente3);
        empresa.getClientes().put("LoloProd", cliente4);
        empresa.getClientes().put("Annaul", cliente5);

        Auto auto = new Auto("AB480HH", 4, false);
        Combi combi = new Combi("AE720HD", 7, true);
        empresa.getVehiculos().put("AB480HH", auto);
        empresa.getVehiculos().put("AE720HD", combi);

        Pedido pedido2 = new Pedido(cliente2, 6, false, false, 10, Constantes.ZONA_STANDARD);
        Pedido pedido4 = new Pedido(cliente4, 9, false, false, 30, Constantes.ZONA_STANDARD);
        Pedido pedido1 = new Pedido(cliente1, 4, false, false, 10, Constantes.ZONA_STANDARD);
        Pedido pedido3 = new Pedido(cliente3, 6, false, false, 10, Constantes.ZONA_STANDARD);
        empresa.getPedidos().put(cliente1, pedido1);
        empresa.getPedidos().put(cliente2, pedido2);
        empresa.getPedidos().put(cliente3, pedido3);
        empresa.getPedidos().put(cliente4, pedido4);

        Viaje viaje = new Viaje(pedido2, chofer_2, combi);
        Viaje viaje2 = new Viaje(pedido4, chofer_2, combi);
        empresa.getViajesIniciados().put(cliente2, viaje);
        empresa.getViajesIniciados().put(cliente4, viaje2);
    }

    @After
    public void tearDown()
    {
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
    public void agregarChoferTest()
    {
        try
        {
            empresa.agregarChofer(empresa.getChoferes().get("777777777"));
            fail("Deberia haber saltado la excepcion de ChoferRepetidoException");
        }
        catch (ChoferRepetidoException e)
        {
        }
    }

    @Test
    public void agregarClienteTest()
    {
        try
        {
            empresa.agregarCliente("Jojo", "123", "Jojo Valotta");
            fail("Deberia haber saltado la excepcion de UsuarioYaExisteException");
        }
        catch (UsuarioYaExisteException e)
        {
        }
    }

    @Test
    public void agregarVehiculoTest()
    {
        try
        {
            empresa.agregarVehiculo(empresa.getVehiculos().get("AB480HH"));
            fail("Deberia haber saltado la excepcion de VehiculoRepetidoException");
        }
        catch (VehiculoRepetidoException e)
        {
        }
    }

    @Test
    public void crearViajeTest1()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        Pedido pedido = empresa.getPedidos().get(cliente);
        Chofer chofer = empresa.getChoferes().get("777777777");
        Auto auto = (Auto) empresa.getVehiculos().get("AB480HH");
        try
        {
            empresa.crearViaje(pedido, chofer, auto);
        }
        catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
               | VehiculoNoValidoException | ClienteConViajePendienteException e)
        {
            fail("No deberia haber saltado ninguna excepcion");
        }
    }

    @Test
    public void crearViajeTest2()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        Pedido pedido = new Pedido(cliente, 2, false, false, 20, Constantes.ZONA_STANDARD);
        Chofer chofer = empresa.getChoferes().get("777777777");
        Auto auto = (Auto) empresa.getVehiculos().get("AB480HH");
        try
        {
            empresa.crearViaje(pedido, chofer, auto);
            fail("Deberia haberse lanzado la excepcion PedidoInexistenteException");
        }
        catch (PedidoInexistenteException e)
        {
        }
        catch (ChoferNoDisponibleException | VehiculoNoDisponibleException | VehiculoNoValidoException | ClienteConViajePendienteException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void crearViajeTest3()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        Pedido pedido = empresa.getPedidos().get(cliente);
        Chofer chofer = new ChoferPermanente("999", "Pepe pedres", 2021, 3);
        Auto auto = (Auto) empresa.getVehiculos().get("AB480HH");
        try
        {
            empresa.crearViaje(pedido, chofer, auto);
            fail("Deberia haberse lanzado la excepcion ChoferNoDisponibleException");
        }
        catch (ChoferNoDisponibleException e)
        {
        }
        catch (PedidoInexistenteException | VehiculoNoDisponibleException | VehiculoNoValidoException | ClienteConViajePendienteException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void crearViajeTest4()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        Pedido pedido = empresa.getPedidos().get(cliente);
        Chofer chofer = empresa.getChoferes().get("777777777");
        Auto auto = new Auto("BB555BB", 4, false);
        try
        {
            empresa.crearViaje(pedido, chofer, auto);
            fail("Deberia haberse lanzado la excepcion VehiculoNoDisponibleException");
        }
        catch (VehiculoNoDisponibleException e)
        {
        }
        catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoValidoException | ClienteConViajePendienteException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void crearViajeTest5()
    {
        Cliente cliente = empresa.getClientes().get("Lehuan20");
        Pedido pedido = empresa.getPedidos().get(cliente);
        Chofer chofer = empresa.getChoferes().get("999999999");
        Auto auto = (Auto) empresa.getVehiculos().get("AB480HH");
        try
        {
            empresa.crearViaje(pedido, chofer, auto);
            fail("Deberia haberse lanzado la excepcion VehiculoNoValidoException");
        }
        catch (VehiculoNoValidoException e)
        {
        }
        catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException | ClienteConViajePendienteException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void crearViajeTest6()
    {
        Cliente cliente = empresa.getClientes().get("Favio");
        Pedido pedido = empresa.getPedidos().get(cliente);
        Chofer chofer = empresa.getChoferes().get("999999999");
        Combi combi = (Combi) empresa.getVehiculos().get("AE720HD");
        try
        {
            empresa.crearViaje(pedido, chofer, combi);
            fail("Deberia haberse lanzado la excepcion ClienteConViajePendienteException");
        }
        catch (ClienteConViajePendienteException e)
        {
        }
        catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException | VehiculoNoValidoException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void pagarYFinalizarViajeTest1()
    {
        Cliente cliente = empresa.getClientes().get("Favio");
        Viaje viaje = empresa.getViajesIniciados().get(cliente);
        try
        {
            empresa.login("Favio", "123");
        }
        catch (UsuarioNoExisteException | PasswordErroneaException e)
        {
        }

        try
        {
            empresa.pagarYFinalizarViaje(5);
            if(empresa.getViajeDeCliente(cliente) != null)
                fail("Se deberia haber terminado el viaje");
            assertEquals("Deberian valer lo mismo", 5, viaje.getCalificacion());
        }
        catch (ClienteSinViajePendienteException e)
        {
            fail("No deberia haberse lanzado esta excepcion");
        }
    }

    @Test
    public void pagarYFinalizarViajeTest2()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        try
        {
            empresa.login("Jojo", "123");
        }
        catch (UsuarioNoExisteException | PasswordErroneaException e)
        {
        }

        try
        {
            empresa.pagarYFinalizarViaje(5);
            fail("Se deberia haber lanzado la excepcion");
        }
        catch (ClienteSinViajePendienteException e)
        {
        }
    }

    @Test
    public void loginTest1()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        try
        {
            empresa.login("Jojo", "holaguillecomoestas");
            fail("Se deberia haber lanzado la excepcion PasswordErroneaException");
        }
        catch (UsuarioNoExisteException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
        catch (PasswordErroneaException e)
        {
        }
    }

    @Test
    public void loginTest2()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        try
        {
            empresa.login("Jojo", "123");
            assertEquals("Deberia ser la misma referencia", empresa.getUsuarioLogeado(), cliente);
        }
        catch (UsuarioNoExisteException | PasswordErroneaException e)
        {
            fail("No se deberia haber lanzado ninguna excepcion");
        }
    }

    @Test
    public void calificacionDeChoferTest1()
    {
        Cliente cliente2 = empresa.getClientes().get("Favio");
        try
        {
            empresa.login("Favio", "123");
        }
        catch (UsuarioNoExisteException | PasswordErroneaException e)
        {
        }

        try
        {
            empresa.pagarYFinalizarViaje(5);
        }
        catch (ClienteSinViajePendienteException e)
        {
        }

        empresa.logout();

        Cliente cliente = empresa.getClientes().get("LoloProd");
        try
        {
            empresa.login("LoloProd", "123");
        }
        catch (UsuarioNoExisteException | PasswordErroneaException e)
        {
        }

        try
        {
            empresa.pagarYFinalizarViaje(3);
        }
        catch (ClienteSinViajePendienteException e)
        {
        }

        empresa.logout();

        try
        {
            assertEquals("Deberian ser iguales", 4, empresa.calificacionDeChofer(empresa.getChoferes().get("999999999")), 0.1);
        }
        catch (SinViajesException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
    }

    @Test
    public void calificacionDeChoferTest2()
    {
        try
        {
            empresa.calificacionDeChofer(empresa.getChoferes().get("777777777"));
            fail("Deberia haberse lanzado la excepcion SinViajesException");
        }
        catch (SinViajesException e)
        {
        }
    }

    @Test
    public void logoutTest()
    {
        Cliente cliente = empresa.getClientes().get("Jojo");
        try
        {
            empresa.login("Jojo", "123");
        }
        catch (UsuarioNoExisteException | PasswordErroneaException e)
        {
        }

        empresa.logout();
        assertEquals("Deberian ser null por no haber un usuario logueado", null, empresa.getUsuarioLogeado());
    }

    @Test
    public void getTotalSalariosTest()
    {
        assertEquals("Deberian valer lo mismo", 243.38, empresa.getTotalSalarios(), 0.001);
    }

    @Test
    public void agregarPedidoTest1()
    {
        Cliente cliente = empresa.getClientes().get("Annaul");
        Pedido pedido = new Pedido(cliente, 7, false, false, 42, Constantes.ZONA_STANDARD);
        try
        {
            empresa.agregarPedido(pedido);
        }
        catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException| ClienteConPedidoPendienteException e)
        {
            fail("No se deberia haber lanzado ninguna excepcion");
        }
    }

    @Test
    public void agregarPedidoTest2()
    {
        Cliente cliente = empresa.getClientes().get("Annaul");
        Pedido pedido = new Pedido(cliente, 9, true, true, 42, Constantes.ZONA_STANDARD);
        try
        {
            empresa.agregarPedido(pedido);
            fail("Se deberia haber lanzado la excepcion SinVehiculoParaPedidoException");
        }
        catch (SinVehiculoParaPedidoException e)
        {
        }
        catch (ClienteNoExisteException | ClienteConViajePendienteException | ClienteConPedidoPendienteException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
    }

    @Test
    public void agregarPedidoTest3()
    {
        Cliente cliente = empresa.getClientes().get("Favio");
        Pedido pedido = new Pedido(cliente, 6, false, false, 42, Constantes.ZONA_STANDARD);
        try
        {
            empresa.agregarPedido(pedido);
            fail("Se deberia haber lanzado la excepcion ClienteConViajePendienteException");
        }
        catch (ClienteConViajePendienteException e)
        {
        }
        catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConPedidoPendienteException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
    }

    @Test
    public void agregarPedidoTest4()
    {
        Cliente cliente = empresa.getClientes().get("Lehuan20");
        Pedido pedido = new Pedido(cliente, 6, false, false, 42, Constantes.ZONA_STANDARD);
        try
        {
            empresa.agregarPedido(pedido);
            fail("Se deberia haber lanzado la excepcion ClienteConPedidoPendienteException");
        }
        catch (ClienteConPedidoPendienteException e)
        {
        }
        catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
    }

    @Test
    public void agregarPedidoTest5()
    {
        Cliente cliente = new Cliente("extra", "0101", "Soy Auxiliar");
        Pedido pedido = new Pedido(cliente, 6, false, false, 42, Constantes.ZONA_STANDARD);
        try
        {
            empresa.agregarPedido(pedido);
            fail("Se deberia haber lanzado la excepcion ClienteNoExisteException");
        }
        catch (ClienteNoExisteException e)
        {
        }
        catch (SinVehiculoParaPedidoException | ClienteConPedidoPendienteException | ClienteConViajePendienteException e)
        {
            fail("No se deberia haber lanzado esta excepcion");
        }
    }
}


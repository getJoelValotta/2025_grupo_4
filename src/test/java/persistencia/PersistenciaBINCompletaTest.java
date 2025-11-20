package persistencia;

import static util.Constantes.ZONA_PELIGROSA;
import static util.Constantes.ZONA_SIN_ASFALTAR;
import static util.Constantes.ZONA_STANDARD;

import java.util.*;

import org.junit.*;
import modeloNegocio.Empresa;
import persistencia.EmpresaDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;

public class PersistenciaBINCompletaTest {
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
        ArrayList<Cliente> clientesDatos = new ArrayList<Cliente>();
        clientesDatos.add(new Cliente("Doni", "1234", "Donatelo"));
        clientesDatos.add(new Cliente("Leo", "aguantelapizza", "Leonardo"));
        clientesDatos.add(new Cliente("Maiky", "kowabunga", "Miguel Angel"));
        clientesDatos.add(new Cliente("Rafafa", "splinter_boton!","Rafael"));
        choferesTest.add(new ChoferPermanente("987789", "Valentino Rossi", 1900, 0));
        choferesTest.add(new ChoferTemporario ( "23900189" , "Franco Colapinto"));
        choferesTest.add(new ChoferPermanente("776677", "Marc Marquez", 2000, 4));
        choferesTest.add(new ChoferTemporario ( "93456712" , "'Pechito' Lopez"));
        vehiculosTest.add(new Combi("AC715PT" , 10, false));
        vehiculosTest.add(new Auto("UF457FU" , 4, true));
        vehiculosTest.add(new Moto( "VA615AS"));
        vehiculosTest.add(new Combi("PE905AS" , 8, false));

        for (Cliente cliente : clientesDatos){
            try {
                empresa.agregarCliente(cliente.getNombreUsuario(), cliente.getPass(), cliente.getNombreReal());
                clientesTest.add(empresa.getClientes().get(cliente.getNombreUsuario()));
            } catch (UsuarioYaExisteException e) {}
        }

        for (Chofer chofer : choferesTest) {
            try {
                empresa.agregarChofer(chofer);
            } catch (ChoferRepetidoException e) {}
        }

        for (Vehiculo vehiculo : vehiculosTest) {
            try {
                empresa.agregarVehiculo(vehiculo);
            } catch (VehiculoRepetidoException e) {}
        }

        ArrayList<Viaje> viajesDatos = new ArrayList<Viaje>();

        viajesDatos.add(new Viaje(new Pedido(clientesTest.get(1), 1, false, false, 290, ZONA_SIN_ASFALTAR), choferesTest.get(0) ,vehiculosTest.get(2)));
        viajesDatos.add(new Viaje(new Pedido(clientesTest.get(0), 3, false, false, 90, ZONA_STANDARD), choferesTest.get(1) ,vehiculosTest.get(0)));
        viajesDatos.add(new Viaje(new Pedido(clientesTest.get(1), 8, false, false, 90, ZONA_SIN_ASFALTAR), choferesTest.get(1) ,vehiculosTest.get(3)));

        for (Viaje viaje : viajesDatos) {
            Cliente clienteViaje = viaje.getPedido().getCliente();
            try {
                empresa.agregarPedido(viaje.getPedido());
            } catch(ClienteConPedidoPendienteException | ClienteConViajePendienteException
                    | ClienteNoExisteException | SinVehiculoParaPedidoException e) {}

            try {
                empresa.crearViaje(viaje.getPedido(), viaje.getChofer(), viaje.getVehiculo());
                viajesTerminadosTest.add(empresa.getViajesIniciados().get(viaje.getPedido().getCliente()));
            } catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
                     | VehiculoNoValidoException | ClienteConViajePendienteException e) {}

            try {
                empresa.login(clienteViaje.getNombreUsuario(), clienteViaje.getPass());
            } catch (UsuarioNoExisteException | PasswordErroneaException e) {}

            try {
                empresa.pagarYFinalizarViaje(4);
            } catch (ClienteSinViajePendienteException e) {}

            empresa.logout();

        }

        viajesDatos = new ArrayList<Viaje>();

        viajesDatos.add(new Viaje(new Pedido(clientesTest.get(2), 1, false, true, 1000, ZONA_PELIGROSA), choferesTest.get(2) ,vehiculosTest.get(1)));
        viajesDatos.add(new Viaje(new Pedido(clientesTest.get(1), 1, false, false, 100, ZONA_PELIGROSA), choferesTest.get(3) ,vehiculosTest.get(2)));
        viajesDatos.add(new Viaje(new Pedido(clientesTest.get(3), 9, false, true, 800, ZONA_STANDARD), choferesTest.get(1) ,vehiculosTest.get(0)));

        for (Viaje viaje : viajesDatos) {
            try {
                empresa.agregarPedido(viaje.getPedido());
            } catch(ClienteConPedidoPendienteException | ClienteConViajePendienteException
                    | ClienteNoExisteException | SinVehiculoParaPedidoException e) {
            }

            try {
                empresa.crearViaje(viaje.getPedido(), viaje.getChofer(), viaje.getVehiculo());
                viajesIniciadosTest.add(empresa.getViajesIniciados().get(viaje.getPedido().getCliente()));
            } catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
                     | VehiculoNoValidoException | ClienteConViajePendienteException e) {}
        }

        pedidosTest.add(new Pedido(clientesTest.get(0), 4, true, false, 50, ZONA_PELIGROSA));

        for (Pedido pedido : pedidosTest) {
            try {
                empresa.agregarPedido(pedido);
            } catch(ClienteConPedidoPendienteException | ClienteConViajePendienteException
                    | ClienteNoExisteException | SinVehiculoParaPedidoException e) {
            }
        }

        empresaDTO.setChoferes(new HashMap<String, Chofer>(empresa.getChoferes()));
        empresaDTO.setChoferesDesocupados(new ArrayList<Chofer>(empresa.getChoferesDesocupados()));
        empresaDTO.setClientes(new HashMap<String, Cliente>(empresa.getClientes()));
        empresaDTO.setPedidos(new HashMap<Cliente, Pedido>(empresa.getPedidos()));
        empresaDTO.setUsuarioLogeado(empresa.getUsuarioLogeado());
        empresaDTO.setVehiculos(new HashMap<String, Vehiculo>(empresa.getVehiculos()));
        empresaDTO.setVehiculosDesocupados(new ArrayList<Vehiculo>(empresa.getVehiculosDesocupados()));
        empresaDTO.setViajesIniciados(new HashMap<Cliente, Viaje>(empresa.getViajesIniciados()));
        empresaDTO.setViajesTerminados(new ArrayList<Viaje>(empresa.getViajesTerminados()));
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
    public void EmpresaDTOfromEmpresaTest() {
        EmpresaDTO empresaDTOTest = UtilPersistencia.EmpresaDtoFromEmpresa();
        Assert.assertEquals("Fallaron los choferes", empresaDTO.getChoferes(), empresaDTOTest.getChoferes());
        Assert.assertEquals("Fallaron los clientes", empresaDTO.getClientes(), empresaDTOTest.getClientes());
        Assert.assertEquals("Fallaron los choferes desocupados", empresaDTO.getChoferesDesocupados(), empresaDTOTest.getChoferesDesocupados());
        Assert.assertEquals("Fallaron los pedidos", empresaDTO.getPedidos(), empresaDTOTest.getPedidos());
        Assert.assertEquals("Fallaron los usuarios loggueados", empresaDTO.getUsuarioLogeado(), empresaDTOTest.getUsuarioLogeado());
        Assert.assertEquals("Fallaron los vehiculos", empresaDTO.getVehiculos(), empresaDTOTest.getVehiculos());
        Assert.assertEquals("Fallaron los vehiculos desocupados", empresaDTO.getVehiculosDesocupados(), empresaDTOTest.getVehiculosDesocupados());
        Assert.assertEquals("Fallaron los viajes iniciados", empresaDTO.getViajesIniciados(), empresaDTOTest.getViajesIniciados());
        Assert.assertEquals("Fallaron los viajes terminados", empresaDTO.getViajesTerminados(), empresaDTOTest.getViajesTerminados());
    }

    @Test
    public void EmpresafromEmpresaDTOTest() { //Pasar del DTO a empresa
        UtilPersistencia.empresaFromEmpresaDTO(empresaDTO);
        Empresa empresa = Empresa.getInstance();
        Assert.assertEquals("Fallaron los choferes", empresaDTO.getChoferes(), empresa.getChoferes());
        Assert.assertEquals("Fallaron los clientes", empresaDTO.getClientes(), empresa.getClientes());
        Assert.assertEquals("Fallaron los choferes desocupados", empresaDTO.getChoferesDesocupados(), empresa.getChoferesDesocupados());
        Assert.assertEquals("Fallaron los pedidos", empresaDTO.getPedidos(), empresa.getPedidos());
        Assert.assertEquals("Fallaron los usuarios loggueados", empresaDTO.getUsuarioLogeado(), empresa.getUsuarioLogeado());
        Assert.assertEquals("Fallaron los vehiculos", empresaDTO.getVehiculos(), empresa.getVehiculos());
        Assert.assertEquals("Fallaron los vehiculos desocupados", empresaDTO.getVehiculosDesocupados(), empresa.getVehiculosDesocupados());
        Assert.assertEquals("Fallaron los viajes iniciados", empresaDTO.getViajesIniciados(), empresa.getViajesIniciados());
        Assert.assertEquals("Fallaron los viajes terminados", empresaDTO.getViajesTerminados(), empresa.getViajesTerminados());
    }

    @Test
    public void CrearArchivoEscrituraTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            persistencia.abrirOutput("persis_test.bin");
            File archivo = new File("persis_test.bin");
            persistencia.cerrarOutput();
            Assert.assertTrue("No existe el archivo persis_test.bin", archivo.exists());
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    @Test
    public void AbrirArchivoNoExisteTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            persistencia.abrirInput("persis_test.bin");
            File archivo = new File("persis_test.bin");
            persistencia.cerrarInput();
            Assert.assertTrue("No debe existir el archivo empresaTest.bin", archivo.exists());
        } catch (IOException e) {
        }
    }

    @Test
    public void EscribirArchivoNoExisteTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            persistencia.escribir(empresaDTO);
            Assert.fail("Debería lanzar una excepcion IOException");
        } catch (IOException e) {
        }
    }

    @Test
    public void LeerArchivoNoExisteTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            try {
                persistencia.leer();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Assert.fail("Debería lanzar una excepcion IOException");
        } catch (IOException e) {
        }
    }

    /*Todos los tests siguientes son de persistencia de las clases individuales*/

    @Test
    public void ChoferPermanenteTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresaDTOLeido = (EmpresaDTO) persistencia.leer();
            UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeido);
            persistencia.cerrarInput();
            Empresa empresa = Empresa.getInstance();
            ChoferPermanente chofer_en_disco = (ChoferPermanente) empresaDTO.getChoferes().get("987789");
            ChoferPermanente chofer_en_memoria = (ChoferPermanente) empresa.getChoferes().get("987789");
            List<AbstractMap.SimpleEntry<String, Boolean>> asertosChoferes = Arrays.asList(
                    new AbstractMap.SimpleEntry<>("El anio de ingreso es distinto", chofer_en_disco.getAnioIngreso() == chofer_en_memoria.getAnioIngreso()),
                    new AbstractMap.SimpleEntry<>("La antiguedad de los choferes es distinta", chofer_en_disco.getAntiguedad() == chofer_en_memoria.getAntiguedad()),
                    new AbstractMap.SimpleEntry<>("La cantidad de hijos de los choferes es distinta", chofer_en_disco.getCantidadHijos() == chofer_en_memoria.getCantidadHijos()),
                    new AbstractMap.SimpleEntry<>("Los dni de los choferes son distintos", chofer_en_disco.getDni().equals(chofer_en_memoria.getDni())),
                    new AbstractMap.SimpleEntry<>("Los nombres de los choferes son distintos", chofer_en_disco.getNombre().equals(chofer_en_memoria.getNombre())),
                    new AbstractMap.SimpleEntry<>("El sueldo bruto de los choferes es distinto", chofer_en_disco.getSueldoBruto() == chofer_en_memoria.getSueldoBruto()),
                    new AbstractMap.SimpleEntry<>("El sueldo neto de los choferes es distinto", chofer_en_disco.getSueldoNeto() == chofer_en_memoria.getSueldoNeto())
            );
            manejarAsertos(asertosChoferes);
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    @Test
    public void ChoferTemporarioTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresaDTOLeido = (EmpresaDTO) persistencia.leer();
            UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeido);
            persistencia.cerrarInput();
            Empresa empresa = Empresa.getInstance();

            ChoferTemporario chofer_en_disco = (ChoferTemporario) empresaDTO.getChoferes().get("23900189");
            ChoferTemporario chofer_en_memoria = (ChoferTemporario) empresa.getChoferes().get("23900189");

            List<AbstractMap.SimpleEntry<String, Boolean>> asertosChoferTemporario = Arrays.asList(
                    new AbstractMap.SimpleEntry<>("Los dni de los choferes son distintos", chofer_en_disco.getDni().equals(chofer_en_memoria.getDni())),
                    new AbstractMap.SimpleEntry<>("Los nombres de los choferes son distintos", chofer_en_disco.getNombre().equals(chofer_en_memoria.getNombre())),
                    new AbstractMap.SimpleEntry<>("El sueldo bruto de los choferes es distinto", chofer_en_disco.getSueldoBruto() == chofer_en_memoria.getSueldoBruto()),
                    new AbstractMap.SimpleEntry<>("El sueldo neto de los choferes es distinto", chofer_en_disco.getSueldoNeto() == chofer_en_memoria.getSueldoNeto())
            );

            manejarAsertos(asertosChoferTemporario);
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    @Test
    public void AutoTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresaDTOLeido = (EmpresaDTO) persistencia.leer();
            UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeido);
            persistencia.cerrarInput();
            Empresa empresa = Empresa.getInstance();

            Auto auto_en_disco = (Auto) empresaDTO.getVehiculos().get("UF457FU");
            Auto auto_en_memoria = (Auto) empresa.getVehiculos().get("UF457FU");

            List<AbstractMap.SimpleEntry<String, Boolean>> asertosAuto = Arrays.asList(
                    new AbstractMap.SimpleEntry<>("La cantidad de plazas del auto es distinta", auto_en_disco.getCantidadPlazas() == auto_en_memoria.getCantidadPlazas()),
                    new AbstractMap.SimpleEntry<>("La patente del auto es distinta", auto_en_disco.getPatente().equals(auto_en_memoria.getPatente())),
                    new AbstractMap.SimpleEntry<>("El valor de mascota del auto es distinta", auto_en_disco.isMascota() == auto_en_memoria.isMascota())
            );

            manejarAsertos(asertosAuto);
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    @Test
    public void CombiTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresaDTOLeido = (EmpresaDTO) persistencia.leer();
            UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeido);
            persistencia.cerrarInput();
            Empresa empresa = Empresa.getInstance();

            Combi combi_en_disco = (Combi) empresaDTO.getVehiculos().get("PE905AS");
            Combi combi_en_memoria = (Combi) empresa.getVehiculos().get("PE905AS");

            List<AbstractMap.SimpleEntry<String, Boolean>> asertosCombi = Arrays.asList(
                    new AbstractMap.SimpleEntry<>("La cantidad de plazas de la combi es distinta", combi_en_disco.getCantidadPlazas() == combi_en_memoria.getCantidadPlazas()),
                    new AbstractMap.SimpleEntry<>("La patente de la combi es distinta", combi_en_disco.getPatente().equals(combi_en_memoria.getPatente())),
                    new AbstractMap.SimpleEntry<>("El valor de mascota de la combi es distinta", combi_en_disco.isMascota() == combi_en_memoria.isMascota())
            );

            manejarAsertos(asertosCombi);
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    @Test
    public void MotoTest() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresaDTOLeido = (EmpresaDTO) persistencia.leer();
            UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeido);
            persistencia.cerrarInput();
            Empresa empresa = Empresa.getInstance();

            Moto moto_en_disco = (Moto) empresaDTO.getVehiculos().get("VA615AS");
            Moto moto_en_memoria = (Moto) empresa.getVehiculos().get("VA615AS");

            List<AbstractMap.SimpleEntry<String, Boolean>> asertosMoto = Arrays.asList(
                    new AbstractMap.SimpleEntry<>("La cantidad de plazas de la moto es distinta", moto_en_disco.getCantidadPlazas() == moto_en_memoria.getCantidadPlazas()),
                    new AbstractMap.SimpleEntry<>("La patente de la moto es distinta", moto_en_disco.getPatente().equals(moto_en_memoria.getPatente())),
                    new AbstractMap.SimpleEntry<>("El valor de mascota de la moto es distinta", moto_en_disco.isMascota() == moto_en_memoria.isMascota())
            );

            manejarAsertos(asertosMoto);
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    @Test
    public void testViaje() {
        PersistenciaBIN persistencia = new PersistenciaBIN();
        try {
            EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.abrirOutput("persis_test.bin");
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();
            persistencia.abrirInput("persis_test.bin");
            EmpresaDTO empresaDTOLeido = (EmpresaDTO) persistencia.leer();
            UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeido);
            persistencia.cerrarInput();
            Empresa empresa = Empresa.getInstance();

            Viaje viaje_en_disco = (Viaje) empresaDTO.getViajesTerminados().get(0);
            Viaje viaje_en_memoria = (Viaje) empresa.getViajesTerminados().get(0);

            List<AbstractMap.SimpleEntry<String, Boolean>> asertosViajes = Arrays.asList(
                    new AbstractMap.SimpleEntry<>("La calificacion de los viajes es distinta", viaje_en_disco.getCalificacion() == viaje_en_memoria.getCalificacion()),
                    new AbstractMap.SimpleEntry<>("Los choferes de los viajes son distintos", viaje_en_disco.getChofer().getDni().equals(viaje_en_memoria.getChofer().getDni())),
                    new AbstractMap.SimpleEntry<>("El valor de los viajes son distintos", viaje_en_disco.getValor() == viaje_en_memoria.getValor()),
                    new AbstractMap.SimpleEntry<>("El vehiculo de los viajes son distintos", viaje_en_disco.getVehiculo().getPatente().equals(viaje_en_memoria.getVehiculo().getPatente())),
                    new AbstractMap.SimpleEntry<>("El cliente de los viajes son distintos", viaje_en_disco.getPedido().getCliente().getNombreUsuario().equals(viaje_en_memoria.getPedido().getCliente().getNombreUsuario()))
            );

            manejarAsertos(asertosViajes);
        } catch (IOException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("No deberia lanzar excepcion: "+ e.getMessage());
        }
    }

    public static void manejarAsertos(List<AbstractMap.SimpleEntry<String, Boolean>> aserciones) {
        StringBuilder errores = new StringBuilder();
        for (AbstractMap.SimpleEntry<String, Boolean> asercion : aserciones) {
            if (!asercion.getValue()) {
                errores.append(asercion.getKey()).append("\n");
            }
        }

        if (errores.length() > 0) {
            Assert.fail("Errores encontrados:\n" + errores.toString());
        }
    }
}


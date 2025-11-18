package persistencia;

import persistencia.*;
import modeloNegocio.Empresa;
import modeloDatos.*;
import org.junit.*;
import static org.junit.Assert.*;

public class UtilPersistenciaTest {
    Empresa empresa = null;

    @Before
    public void setUp() throws Exception {

    }

    //Escenario 1: La Empresa no se ha instanciado nunca (empresa == null)
    //Escenario 2: La Empresa se ha instanciado una unica vez (empresa != null)
    @Test
    public void EmpresaDTOFromEmpresaTest_1(){  //Cubre escenario 1
        EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
        assertNull(empresaDTO);
    }

    @Test
    public void EmpresaDTOFromEmpresaTest_2(){  //Cubre escenario 2
        this.empresa = Empresa.getInstance();
        EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
        assertNotNull(empresaDTO);
    }

    @Test
    public void EmpresaFromEmpresaDTOTest() {
        Empresa empresaAct = Empresa.getInstance();
        EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
        Empresa empresaAnt = empresaAct;
        UtilPersistencia.empresaFromEmpresaDTO(empresaDTO);
        assertEquals(empresaAnt, empresaAct);
    }
}

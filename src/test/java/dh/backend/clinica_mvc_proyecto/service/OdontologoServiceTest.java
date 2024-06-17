package dh.backend.clinica_mvc_proyecto.service;

import dh.backend.clinica_mvc_proyecto.entity.Odontologo;

import dh.backend.clinica_mvc_proyecto.service.impl.OdontologoService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    private OdontologoService odontologoService;

    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNroMatricula("5164721");
        odontologo.setNombre("juliana");
        odontologo.setApellido("vargas");
        odontologo = odontologoService.registrarOdontologo(odontologo);
    }

    @Test
    @DisplayName("se ingresa un odontologo")
    void ingreso() {
        Assertions.assertNotNull(odontologoService.registrarOdontologo(odontologo ));
    }

    @Test
    @DisplayName("listar todos odontologos")
    void Testlistar() {
        List<Odontologo> listar = odontologoService.buscarTodos();
        assertTrue(listar.size()!=0);

    }

    @Test
    @DisplayName("Testear busqueda Odontologo por id")
    void testOdontologoPorId() {
        Integer id = 1;
        Optional<Odontologo> odontologoOptional = odontologoService.buscarPorId(id);
        Odontologo odontologoEncontrado= odontologoOptional.get();

        assertEquals(id, odontologoEncontrado.getId());
    }
}

package dh.backend.clinica_mvc_proyecto.service;

import dh.backend.clinica_mvc_proyecto.Dto.request.TurnoRequestDto;
import dh.backend.clinica_mvc_proyecto.Dto.response.TurnoResponseDto;
import dh.backend.clinica_mvc_proyecto.entity.Odontologo;
import dh.backend.clinica_mvc_proyecto.entity.Paciente;
import dh.backend.clinica_mvc_proyecto.exception.BadRequestException;
import dh.backend.clinica_mvc_proyecto.service.impl.OdontologoService;
import dh.backend.clinica_mvc_proyecto.service.impl.PacienteService;
import dh.backend.clinica_mvc_proyecto.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoServiceTest {

    private static Logger LOGGER= LoggerFactory.getLogger(TurnoServiceTest.class);

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    private TurnoResponseDto turnoResponseDto;
    private TurnoRequestDto turnoRequestDto;
    private Odontologo odontologo;
    private Paciente paciente;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNroMatricula("5164721");
        odontologo.setNombre("juliana");
        odontologo.setApellido("vargas");
        odontologoService.registrarOdontologo(odontologo);

        paciente = new Paciente();
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        pacienteService.registrarPaciente(paciente);

        turnoRequestDto= new TurnoRequestDto();
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setFecha("2024-02-21");
    }

    @Test
    @DisplayName("Testear que un turno fue guardado")
    void testTurnoGuardado() throws BadRequestException {
        TurnoResponseDto turnoResponseDto= turnoService.registrar(turnoRequestDto);
        assertNotNull(turnoResponseDto);
    }

}

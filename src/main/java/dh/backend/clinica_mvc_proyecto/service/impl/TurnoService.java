package dh.backend.clinica_mvc_proyecto.service.impl;

import dh.backend.clinica_mvc_proyecto.Dto.request.TurnoRequestDto;
import dh.backend.clinica_mvc_proyecto.Dto.response.OdontologoResponseDto;
import dh.backend.clinica_mvc_proyecto.Dto.response.PacienteResponseDto;
import dh.backend.clinica_mvc_proyecto.Dto.response.TurnoResponseDto;
import dh.backend.clinica_mvc_proyecto.entity.Odontologo;
import dh.backend.clinica_mvc_proyecto.entity.Paciente;
import dh.backend.clinica_mvc_proyecto.entity.Turno;
import dh.backend.clinica_mvc_proyecto.exception.BadRequestException;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import dh.backend.clinica_mvc_proyecto.repository.IOdontologoRepository;
import dh.backend.clinica_mvc_proyecto.repository.IPacienteRepository;
import dh.backend.clinica_mvc_proyecto.repository.ITurnoRepository;
import dh.backend.clinica_mvc_proyecto.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private ITurnoRepository turnoRepository;
    private IPacienteRepository pacienteRepository;
    private IOdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    public TurnoService(ITurnoRepository turnoRepository, IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) throws BadRequestException {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;
        if(paciente.isPresent() && odontologo.isPresent()){
            turnoARegistrar.setOdontologo(odontologo.get());
            turnoARegistrar.setPaciente(paciente.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);

            turnoADevolver= mapToResponseDto(turnoGuardado);
            LOGGER.info("Turno creado"+ turnoADevolver);


        }else {
            throw new BadRequestException("{\"message\": \"Paciente  o Odontologo no existe\"}");
        }
        return turnoADevolver;
    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) {
        Optional<Turno> turnoOptional= turnoRepository.findById(id);
        if(turnoOptional.isPresent()){
            Turno turnoEncontrado= turnoOptional.get();
            TurnoResponseDto turnoADevolver=mapToResponseDto(turnoEncontrado);
            LOGGER.info("Turno encontrado por ID" + turnoADevolver);
            return turnoADevolver;
        }
        return null;
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for(Turno turno: turnos){
            turnoAuxiliar = mapToResponseDto(turno);
            turnosADevolver.add(turnoAuxiliar);
            LOGGER.info("lista de turnos" + turnoAuxiliar);
        }

        return turnosADevolver;
    }

    @Override
    public void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Optional<Turno> turno= turnoRepository.findById(id);
        Turno turnoAModificar = new Turno();
        if(paciente.isPresent() && odontologo.isPresent() && turno.isPresent()){
            turnoAModificar.setId(id);
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoRepository.save(turnoAModificar);
            LOGGER.info("Turno actualizado");
        }
    }

    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        TurnoResponseDto turnoResponseDto = buscarPorId(id);
        if(turnoResponseDto !=null) {
            turnoRepository.deleteById(id);
            LOGGER.info("Turno eliminado");
        }
        else
            throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnoEntreFechas(startDate, endDate);
        List<TurnoResponseDto> listadoARetornar = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno: listadoTurnos){
            turnoAuxiliar = mapToResponseDto(turno);
            listadoARetornar.add(turnoAuxiliar);
            LOGGER.info("Turno disponible" + turnoAuxiliar);
        }
        return listadoARetornar;
    }

    @Override
    public List<TurnoResponseDto> buscarFechaPosterior(LocalDate startDate) {
        List<Turno> listaTurnos= turnoRepository.buscarFechaPosterior(startDate);
        List<TurnoResponseDto> listadoRetornar= new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno:listaTurnos) {
            turnoAuxiliar= mapToResponseDto(turno);
            listadoRetornar.add(turnoAuxiliar);
            LOGGER.info("Turno disponible fecha posterior" + turnoAuxiliar);
        }
        return listadoRetornar;
    }

    private TurnoResponseDto mapToResponseDto(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return  turnoResponseDto;
    }
}

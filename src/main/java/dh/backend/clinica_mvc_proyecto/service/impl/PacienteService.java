package dh.backend.clinica_mvc_proyecto.service.impl;

import dh.backend.clinica_mvc_proyecto.entity.Paciente;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import dh.backend.clinica_mvc_proyecto.repository.IPacienteRepository;
import dh.backend.clinica_mvc_proyecto.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Integer id){
        Optional<Paciente> pacienteOptional= pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()){
            LOGGER.info("paciente encontrado" + pacienteOptional);
        }
        return pacienteOptional;
    }

    public List<Paciente> buscarTodos(){
        List<Paciente> pacienteLista= pacienteRepository.findAll();
        LOGGER.info("pacientes encontrados"+ pacienteLista);
        return pacienteLista;
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
        LOGGER.info("Paciente actualizado");
    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = buscarPorId(id);
        if(pacienteOptional.isPresent()) {
            pacienteRepository.deleteById(id);
            LOGGER.info("Paciente eliminado");
        }
        else
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
    }

    @Override
    public Paciente buscarPorDni(String dni) {
        Paciente pacienteDni= pacienteRepository.buscarPorDni(dni);
        if (pacienteDni!= null){
            LOGGER.info("Paciente encontrado"+pacienteDni);
        }
        return pacienteDni;
    }

    @Override
    public List<Paciente> buscarPorProvincia(String provincia) {
        List<Paciente> listaProvincia= pacienteRepository.buscarProvincia(provincia);
        LOGGER.info("Paciente por provincia" + listaProvincia);
        return listaProvincia;
    }
}

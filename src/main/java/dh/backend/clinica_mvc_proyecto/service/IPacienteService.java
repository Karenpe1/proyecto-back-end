package dh.backend.clinica_mvc_proyecto.service;

import dh.backend.clinica_mvc_proyecto.entity.Paciente;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente registrarPaciente(Paciente paciente);

    Optional<Paciente> buscarPorId(Integer id);

    List<Paciente> buscarTodos();
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Integer id) throws ResourceNotFoundException, ResourceNotFoundException;
    Paciente buscarPorDni (String dni);

    List<Paciente> buscarPorProvincia(String provincia);
}

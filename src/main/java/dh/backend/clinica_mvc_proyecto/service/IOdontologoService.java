package dh.backend.clinica_mvc_proyecto.service;


import dh.backend.clinica_mvc_proyecto.entity.Odontologo;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);

    List<Odontologo> buscarTodos();

    Optional<Odontologo> buscarPorId(Integer id);
    void actualizarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Integer id) throws ResourceNotFoundException;
    List<Odontologo> buscarPorApellido(String apellido);
    List<Odontologo> buscarPorNombre(String nombre);
}

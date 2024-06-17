package dh.backend.clinica_mvc_proyecto.service;

import dh.backend.clinica_mvc_proyecto.Dto.request.TurnoRequestDto;
import dh.backend.clinica_mvc_proyecto.Dto.response.TurnoResponseDto;
import dh.backend.clinica_mvc_proyecto.exception.BadRequestException;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;


import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {
    TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) throws BadRequestException;

    TurnoResponseDto buscarPorId(Integer id);

    List<TurnoResponseDto> buscarTodos();
    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto);
    void eliminarTurno(Integer id) throws ResourceNotFoundException, ResourceNotFoundException;
    List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate);
    List<TurnoResponseDto> buscarFechaPosterior(LocalDate startDate);
}

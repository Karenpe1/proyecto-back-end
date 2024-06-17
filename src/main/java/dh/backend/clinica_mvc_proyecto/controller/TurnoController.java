package dh.backend.clinica_mvc_proyecto.controller;

import dh.backend.clinica_mvc_proyecto.Dto.request.TurnoRequestDto;
import dh.backend.clinica_mvc_proyecto.Dto.response.TurnoResponseDto;
import dh.backend.clinica_mvc_proyecto.exception.BadRequestException;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import dh.backend.clinica_mvc_proyecto.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoResponseDto> agregarTurno(@RequestBody TurnoRequestDto turnoRequestDto) throws BadRequestException {
        return ResponseEntity.ok(turnoService.registrar(turnoRequestDto));
    }
    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosturnos(){
        return  ResponseEntity.ok(turnoService.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarPorIdTurno(@PathVariable Integer id){
        TurnoResponseDto turno = turnoService.buscarPorId(id);
        if(turno == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(turno);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarturno(@PathVariable Integer id,@RequestBody TurnoRequestDto turno){
        turnoService.actualizarTurno(id, turno);
        return ResponseEntity.ok("Turno modificado");
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarturno(@PathVariable Integer id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"message\": \"turno eliminado\"}");
    }
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarEntreFechas(@RequestParam String inicio, @RequestParam String fin){
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);

        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio, fechaFinal));
    }

    @GetMapping("/fechaPosterior")
    public ResponseEntity<List<TurnoResponseDto>> buscarFechaPosterior(@RequestParam String inicio){
        LocalDate fechaInicio= LocalDate.parse(inicio,formatter);

        List<TurnoResponseDto> turno= turnoService.buscarFechaPosterior(fechaInicio);
        if (turno.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(turno);


    }

}

package dh.backend.clinica_mvc_proyecto.controller;

import dh.backend.clinica_mvc_proyecto.entity.Paciente;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import dh.backend.clinica_mvc_proyecto.service.IPacienteService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteARetornar= pacienteService.registrarPaciente(paciente);
        if(pacienteARetornar== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteARetornar);
        }

    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(id);
        if(pacienteOptional.isPresent()){
            Paciente pacienteRetorno= pacienteOptional.get();
            return ResponseEntity.ok(pacienteRetorno);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(paciente.getId());
        if (pacienteOptional.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("{\"message\": \"paciente modificado\"}");

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> borrarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
    }
    @GetMapping("/buscarPorDni")
    public  ResponseEntity<Paciente> buscarPorDni(@RequestParam String dni){
        Paciente paciente=pacienteService.buscarPorDni(dni);
        if (paciente== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(paciente);
    }

    @GetMapping("/buscarPorProvincia")
    public  ResponseEntity<List<Paciente>> buscarPorProvincia(@RequestParam String provincia){
        return ResponseEntity.ok(pacienteService.buscarPorProvincia(provincia));
    }
}

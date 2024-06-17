package dh.backend.clinica_mvc_proyecto.controller;

import dh.backend.clinica_mvc_proyecto.entity.Odontologo;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import dh.backend.clinica_mvc_proyecto.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Odontologo> registrar(@RequestBody Odontologo odontologo){
        Odontologo odontologoARetornar = odontologoService.registrarOdontologo(odontologo);
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoARetornar);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Odontologo>> busarTodos(){
        return  ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Odontologo> buscarPorId(@PathVariable Integer id){
        Optional<Odontologo> odontologoOptional= odontologoService.buscarPorId(id);
        if (odontologoOptional.isPresent()){
            Odontologo odontologARetornar= odontologoOptional.get();
            return ResponseEntity.ok(odontologARetornar);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaciente(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOptional = odontologoService.buscarPorId(odontologo.getId());
        if(odontologoOptional.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> borrarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");


    }
    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> listaOdontologos =odontologoService.buscarPorApellido(apellido);
        if(listaOdontologos.size()>0){
            return ResponseEntity.ok(listaOdontologos);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarTodos(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}

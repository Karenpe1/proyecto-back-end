package dh.backend.clinica_mvc_proyecto.service.impl;
import dh.backend.clinica_mvc_proyecto.entity.Odontologo;
import dh.backend.clinica_mvc_proyecto.exception.ResourceNotFoundException;
import dh.backend.clinica_mvc_proyecto.repository.IOdontologoRepository;
import dh.backend.clinica_mvc_proyecto.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private IOdontologoRepository odontologoRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(Odontologo.class);

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo) {
        Odontologo odontologo1= odontologoRepository.save(odontologo);
        if (odontologo1!= null){
            LOGGER.info("odontologo registrado"+ odontologo1);
        }
        return odontologoRepository.save(odontologo);

    }

    public List<Odontologo> buscarTodos() {
        List<Odontologo> odontologoLista= odontologoRepository.findAll();
        LOGGER.info("Odontologos listados"+odontologoLista);
        return odontologoLista;
    }

    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoOptional=odontologoRepository.findById(id);
        if (odontologoOptional.isPresent()){
            LOGGER.info("Odontologo encontrado" + odontologoOptional);
        }
        return odontologoOptional;
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
        LOGGER.info("odontologo actualizado");
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = buscarPorId(id);
        if(odontologoOptional.isPresent()) {
            odontologoRepository.deleteById(id);
            LOGGER.info("Odontologo eliminado");
        }
        else
            throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        List<Odontologo> listaOdontologos= odontologoRepository.buscarPorApellido(apellido);
        LOGGER.info("lista de odontologos por apellido"+ listaOdontologos);
        return listaOdontologos;
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        List<Odontologo> listaOdontologo1=odontologoRepository.findByNombreLike(nombre);
        LOGGER.info("Odontologos por nombre" + listaOdontologo1);
        return listaOdontologo1;
    }

}

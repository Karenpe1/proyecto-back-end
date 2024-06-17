package dh.backend.clinica_mvc_proyecto.repository;

import dh.backend.clinica_mvc_proyecto.entity.Odontologo;
import dh.backend.clinica_mvc_proyecto.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
    @Query("Select p from Paciente p where p.dni = :dni")
    Paciente buscarPorDni(@Param("dni") String dni);

    @Query("Select p from Paciente p join p.domicilio d where d.provincia= :provincia")
    List<Paciente> buscarProvincia(@Param("provincia") String provincia);
}

package dh.backend.clinica_mvc_proyecto.Dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OdontologoResponseDto {
    private Integer id;
    private String nroMatricula;
    private String nombre;
    private String apellido;
}

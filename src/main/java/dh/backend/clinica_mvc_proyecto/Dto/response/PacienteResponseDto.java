package dh.backend.clinica_mvc_proyecto.Dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacienteResponseDto {
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;
}

package mx.edu.utez.proyecto1D.model.persona.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdatePersonaDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 6, max = 20, message = "Debe contener entre 6 y 20 carateres")
    private String nombres;

    @NotBlank(message = "El primer apellido no puede estar vacio")
    private String primerApellido;

    @NotBlank(message = "El segundo apellido no puede estar vacio")
    @Size()
    private String segundoSApellido;
    private LocalDate fechaNAcimiento;

    @NotBlank(message = "La curp no puede estar vacia")
    private String curp;
    private String correo;
}

package mx.edu.utez.proyecto1D.model.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateUsuarioDTO {
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 6, max = 20, message = "Debe contener entre 6 y 20 carateres")
    private String username;

    private String descripcion;
}
package mx.edu.utez.proyecto1D.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1D.model.usuario.Usuario;
import mx.edu.utez.proyecto1D.model.usuario.dto.RequestCreateUserDTO;
import mx.edu.utez.proyecto1D.model.usuario.dto.RequestUpdateUsuarioDTO;
import mx.edu.utez.proyecto1D.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers(){
        return ResponseEntity.status(200).body(usuarioService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(usuarioService.getUserById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Usuario> toggleUserStatus(@PathVariable Long id) {
        return ResponseEntity.status(200).body(usuarioService.toggleUserStatus(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Long id,
            @RequestBody @Valid RequestUpdateUsuarioDTO payload
    ) {
        return ResponseEntity.status(200).body(usuarioService.updateUsernameAndDescripcion(id, payload));
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody @Valid RequestCreateUserDTO payload){
        return ResponseEntity.status(201).body(usuarioService.createUser(payload));
    }
}
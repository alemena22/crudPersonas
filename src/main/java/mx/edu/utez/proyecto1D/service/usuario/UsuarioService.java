package mx.edu.utez.proyecto1D.service.usuario;

import mx.edu.utez.proyecto1D.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.proyecto1D.model.persona.Persona;
import mx.edu.utez.proyecto1D.model.usuario.Roles;
import mx.edu.utez.proyecto1D.model.usuario.Usuario;
import mx.edu.utez.proyecto1D.model.usuario.dto.RequestCreateUserDTO;
import mx.edu.utez.proyecto1D.model.usuario.dto.RequestUpdateUsuarioDTO;
import mx.edu.utez.proyecto1D.repository.persona.PersonaRepository;
import mx.edu.utez.proyecto1D.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    public List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }

    public Usuario getUserById(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new CustomBadRequestException("El usuario con ID " + id + " no existe");
        }
        return optionalUsuario.get();
    }

    @Transactional
    public Usuario toggleUserStatus(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new CustomBadRequestException("El usuario con ID " + id + " no existe");
        }

        Usuario usuario = optionalUsuario.get();
        usuario.setEnable(!usuario.isEnable());

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsernameAndDescripcion(Long id, RequestUpdateUsuarioDTO payload) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new CustomBadRequestException("El usuario con ID " + id + " no existe");
        }

        Usuario usuarioExistente = optionalUsuario.get();

        Optional<Usuario> usuarioConMismoUsername = usuarioRepository.findByUsername(payload.getUsername());
        if (usuarioConMismoUsername.isPresent() && !usuarioConMismoUsername.get().getId().equals(id)) {
            throw new CustomBadRequestException("El username ya está en uso por otro usuario");
        }

        usuarioExistente.setUsername(payload.getUsername());
        usuarioExistente.setDescripcion(payload.getDescripcion());

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public Usuario createUser(RequestCreateUserDTO payload){
        if(usuarioRepository.findByUsername(payload.getUsername()).isPresent()){
            throw new CustomBadRequestException("El username ya esta en uso");
        }

        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombres(payload.getNombres());
        nuevaPersona.setPrimerApellido(payload.getPrimerApellido());
        nuevaPersona.setSegundoSApellido(payload.getSegundoSApellido());
        nuevaPersona.setCorreo(payload.getCorreo());
        nuevaPersona.setCurp(payload.getCurp());
        nuevaPersona.setFechaNAcimiento(payload.getFechaNAcimiento());

        nuevaPersona = personaRepository.save(nuevaPersona);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(payload.getUsername());
        nuevoUsuario.setPassword(payload.getPassword());
        nuevoUsuario.setDescripcion(payload.getDescripcion());
        nuevoUsuario.setEnable(true);
        nuevoUsuario.setRol(Roles.valueOf(payload.getRol()));
        nuevoUsuario.setPersona(nuevaPersona);

        return usuarioRepository.save(nuevoUsuario);
    }
}
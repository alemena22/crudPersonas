package mx.edu.utez.proyecto1D.service.persona;

import mx.edu.utez.proyecto1D.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.proyecto1D.model.persona.Persona;
import mx.edu.utez.proyecto1D.model.persona.dto.RequestUpdatePersonaDTO;
import mx.edu.utez.proyecto1D.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Persona updatePersona(Long id, RequestUpdatePersonaDTO payload) {
        Optional<Persona> optionalPersona = personaRepository.findById(id);
        if (!optionalPersona.isPresent()) {
            throw new CustomBadRequestException("La persona con ID " + id + " no existe");
        }

        Persona personaExistente = optionalPersona.get();

        personaExistente.setNombres(payload.getNombres());
        personaExistente.setPrimerApellido(payload.getPrimerApellido());
        personaExistente.setSegundoSApellido(payload.getSegundoSApellido());
        personaExistente.setFechaNAcimiento(payload.getFechaNAcimiento());
        personaExistente.setCurp(payload.getCurp());
        personaExistente.setCorreo(payload.getCorreo());

        return personaRepository.save(personaExistente);
    }
}
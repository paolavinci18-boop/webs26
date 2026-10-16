package it.webs26.utente;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UtenteResponse> findAll() {
        return utenteRepository.findAll().stream()
                .map(UtenteService::toResponse)
                .toList();
    }

    public UtenteResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    @Transactional
    public UtenteResponse create(UtenteRequest request) {
        Utente utente = new Utente();
        applyRequest(utente, request);
        return toResponse(utenteRepository.saveAndFlush(utente));
    }

    @Transactional
    public UtenteResponse replace(Integer id, UtenteRequest request) {
        Utente utente = findEntityById(id);
        applyRequest(utente, request);
        return toResponse(utenteRepository.saveAndFlush(utente));
    }

    @Transactional
    public void delete(Integer id) {
        utenteRepository.delete(findEntityById(id));
    }

    private Utente findEntityById(Integer id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new UtenteNotFoundException(id));
    }

    private void applyRequest(Utente utente, UtenteRequest request) {
        utente.setUsername(request.username());
        utente.setPassword(passwordEncoder.encode(request.password()));
        utente.setLastname(request.lastname());
        utente.setMail(request.mail());
        utente.setNote(request.note());
    }

    private static UtenteResponse toResponse(Utente utente) {
        return new UtenteResponse(
                utente.getId(),
                utente.getUsername(),
                utente.getLastname(),
                utente.getMail(),
                utente.getNote());
    }
}
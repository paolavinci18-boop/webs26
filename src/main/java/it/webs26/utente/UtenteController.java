package it.webs26.utente;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public List<UtenteResponse> findAll() {
        return utenteService.findAll();
    }

    @GetMapping("/{id}")
    public UtenteResponse findById(@PathVariable Integer id) {
        return utenteService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UtenteResponse> create(@Valid @RequestBody UtenteRequest request) {
        UtenteResponse created = utenteService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public UtenteResponse replace(@PathVariable Integer id, @Valid @RequestBody UtenteRequest request) {
        return utenteService.replace(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        utenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
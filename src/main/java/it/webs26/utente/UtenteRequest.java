package it.webs26.utente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UtenteRequest(
        @NotBlank @Size(max = 45) String username,
        @NotBlank @Size(max = 256) String password,
        @NotBlank @Size(max = 45) String lastname,
        @NotBlank @Email @Size(max = 100) String mail,
        @Size(max = 1000) String note) {
}
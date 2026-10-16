package it.webs26.utente;

public record UtenteResponse(
        Integer id,
        String username,
        String lastname,
        String mail,
        String note) {
}
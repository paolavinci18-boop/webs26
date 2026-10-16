package it.webs26.utente;

public class UtenteNotFoundException extends RuntimeException {

    public UtenteNotFoundException(Integer id) {
        super("Utente con id " + id + " non trovato.");
    }
}
# webs26

Servizio REST basato su Spring Boot, Java 21 e MySQL per la tabella `webs26.t_utenti`.

## Prerequisiti

- JDK 21
- Maven 3.6.3 o successivo
- Un server MySQL raggiungibile con il database `webs26` e la tabella `t_utenti`

## Avvio locale

1. Importa `webs26.sql` nel database MySQL `webs26` e verifica che il server sia attivo.
2. Imposta `DB_PASSWORD` in `.env` con la password effettiva dell'utente MySQL `webs26`.
3. Avvia il servizio: `mvn spring-boot:run`.
4. Verifica il servizio: `GET http://localhost:8080/api/v1/health`.
5. Apri la documentazione REST: `http://localhost:8080/swagger-ui.html`.

Le impostazioni locali si trovano in `.env` (ignorato da Git). Il file viene caricato da Spring Boot come proprietà; Compose usa lo stesso file per inizializzare MySQL.

## API utenti

Base URL: `/api/v1/utenti`.

- `GET /api/v1/utenti`: elenca gli utenti.
- `GET /api/v1/utenti/{id}`: recupera un utente.
- `POST /api/v1/utenti`: crea un utente (risponde `201 Created`).
- `PUT /api/v1/utenti/{id}`: sostituisce i dati dell'utente.
- `DELETE /api/v1/utenti/{id}`: elimina un utente (risponde `204 No Content`).

La password è obbligatoria in creazione e sostituzione, viene salvata con BCrypt e non viene mai inclusa nelle risposte. Il dump fornito contiene invece un utente di esempio con password in chiaro: aggiornalo o rimuovilo prima di usare i dati in un ambiente reale.

Gli errori di validazione restituiscono `400 Bad Request`, gli ID inesistenti `404 Not Found` e le violazioni dei vincoli univoci di username/mail `409 Conflict`.

La connessione usa `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` da `.env` o dall'ambiente. Imposta `DB_PASSWORD` con la password effettiva dell'utente MySQL `webs26`. Hibernate verifica lo schema (`ddl-auto=validate`) ma non lo crea né lo modifica: importa prima `webs26.sql` nel database `webs26`.

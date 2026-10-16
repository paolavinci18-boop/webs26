package it.webs26.utente;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UtenteController.class)
class UtenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UtenteService utenteService;

    @Test
    void findAllReturnsUsersWithoutPasswords() throws Exception {
        given(utenteService.findAll()).willReturn(List.of(user()));

        mockMvc.perform(get("/api/v1/utenti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("mario"))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    void findByIdReturnsUser() throws Exception {
        given(utenteService.findById(1)).willReturn(user());

        mockMvc.perform(get("/api/v1/utenti/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mail").value("mario@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void createReturnsCreatedAndLocation() throws Exception {
        given(utenteService.create(any(UtenteRequest.class))).willReturn(user());

        mockMvc.perform(post("/api/v1/utenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/utenti/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void replaceReturnsUpdatedUser() throws Exception {
        given(utenteService.replace(org.mockito.ArgumentMatchers.eq(1), any(UtenteRequest.class)))
                .willReturn(user());

        mockMvc.perform(put("/api/v1/utenti/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mario"));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/utenti/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(utenteService).delete(1);
    }

    @Test
    void createRejectsInvalidEmail() throws Exception {
        String body = requestBody().replace("mario@example.com", "not-an-email");

        mockMvc.perform(post("/api/v1/utenti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.mail").exists());
    }

    private static UtenteResponse user() {
        return new UtenteResponse(1, "mario", "Rossi", "mario@example.com", null);
    }

    private static String requestBody() {
        return """
                {
                  "username": "mario",
                  "password": "segreto123",
                  "lastname": "Rossi",
                  "mail": "mario@example.com",
                  "note": null
                }
                """;
    }
}
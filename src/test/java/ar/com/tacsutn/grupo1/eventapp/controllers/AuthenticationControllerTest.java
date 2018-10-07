package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest extends ControllerTest {
    @Transactional
    @DirtiesContext
    @Test
    public void canLogin() throws Exception {
        this.getMockMvc()
                .perform(
                        post("/api/v1/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"username\": \"JohnDoemann1\", \"password\": \"1234\"}")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotLoginWithWrongUser() throws Exception {
        this.getMockMvc()
                .perform(
                        post("/api/v1/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"username\": \"JohnDoe\", \"password\": \"1234\"}")
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
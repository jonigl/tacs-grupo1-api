package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ControllerTest {

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetUser() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("JohnDoemann1"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doemann"));

    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetTotalAlarmsOfUser() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/2/total_alarms").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total_alarms").value("1"));
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldGetZeroTotalAlarmsOfNonExistentUser() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/55/total_alarms").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total_alarms").value("0"));

    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetTotalEventsOfUser() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/2/total_events").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total_lists").value("1"));
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canCreateUser() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\": \"a@a.com\", \"firstname\": \"Nombre5\", \"lastAccess\": \"2018-10-08T01:03:50.801Z\", \"lastname\": \"Apellido5\", \"username\": \"user5\", \"password\": \"123456\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}

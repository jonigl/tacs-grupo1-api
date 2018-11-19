package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
public class UserControllerTest extends ControllerTest {

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canGetUsers() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotGetUsersIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostUser() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"email\": \"a@a.com\", \"firstname\": \"Nombre5\", \"lastAccess\": \"2018-10-08T01:03:50.801Z\", \"lastname\": \"Apellido5\", \"username\": \"user5\", \"password\": \"123456\"}"))
                .andExpect(status().isOk());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPostExistingUser() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"email\": \"a@a.com\", \"firstname\": \"Nombre5\", \"lastAccess\": \"2018-10-08T01:03:50.801Z\", \"lastname\": \"Apellido5\", \"username\": \"JohnDoemann1\", \"password\": \"123456\"}"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canGetUserById() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/" + userId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("JohnDoemann1"))
                .andExpect(jsonPath("$.id").value(userId1))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doemann"));

    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotGetUserByIdIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/" + userId1))
                .andExpect(status().isForbidden());

    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void shouldNotGetUserByIdIfDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/500"))
                .andExpect(status().isNotFound());

    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canGetTotalAlarms() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/" + userId1 + "/total_alarms"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotGetTotalAlarmsIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/" + userId1 + "/total_alarms"))
                .andExpect(status().isForbidden());

    }

    @Ignore
    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void shouldNotGetTotalAlarmsIfUserDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/500/total_alarms"))
                .andExpect(status().isNotFound());

    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canGetTotalLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/" + userId1 + "/total_lists"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotGetTotalListsIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/" + userId1 + "/total_lists"))
                .andExpect(status().isForbidden());
    }

    @Ignore
    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void shouldNotGetTotalListsIfUserDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/500/total_lists"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetUsersInfo() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/info"))
                .andExpect(status().isOk());
    }

    @DirtiesContext
    @Test
    public void shouldNotGetUsersInfoIfNotLoggedIn() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/users/info"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPatchUsersInfo() throws Exception {
        this.getMockMvc()
                .perform(patch("/api/v1/users/info")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"username\": \"JohnDoemann1\", \"email\": \"john@foobar.com\", \"firstname\": \"Jhonny\", \"lastname\": \"Foobar\" }")
                )
                .andExpect(status().isOk());
    }
}

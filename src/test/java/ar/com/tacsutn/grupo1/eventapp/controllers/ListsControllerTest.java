package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListsControllerTest extends ControllerTest {
    @WithMockUser(roles = "USER")
    @Transactional
    @Test
    public void canGetLists() throws Exception {
        this.getMockMvc().perform(get("/api/v1/lists")).andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @Test
    public void canGetListsById() throws Exception {
        this.getMockMvc().perform(get("/api/v1/lists/1")).andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @Test
    public void canGetListsEventsById() throws Exception {
        this.getMockMvc().perform(get("/lists/1/events")).andExpect(status().isOk());
    }
}

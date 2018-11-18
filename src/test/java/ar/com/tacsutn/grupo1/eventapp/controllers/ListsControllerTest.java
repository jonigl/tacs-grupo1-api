package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListsControllerTest extends ControllerTest {
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostLists() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"1\"}], \"name\": \"NewList1\" }"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostListsWithNoEvents() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"name\": \"NewList1\" }"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostListsWithManyEvents() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"0\"}, {\"id\": \"1\"}], \"name\": \"NewList1\" }"))
                .andExpect(status().isOk());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPostListsIfHasSameName() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"0\"}], \"name\": \"List1\" }"))
                .andExpect(status().isForbidden());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPostListsIfNoNameGiven() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"1\"}] }"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetListsById() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/" + eventListId1))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPutListsById() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/" + eventListId1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"" + eventId1 + "\"}], \"id\": \"" + eventListId1 + "\", \"name\": \"NewName1\" }"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPutListsByIdWithNoEvents() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/" + eventListId1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\": \"" + eventListId1 + "\", \"name\": \"NewName1\" }"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPutListsByIdWithManyEvents() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/" + eventListId1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"" + eventId0 + "\"}, {\"id\": \"" + eventId1 + "\"}], \"id\": \"" + eventListId1 + "\", \"name\": \"NewName1\" }"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPutListsByIdWithSameName() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/" + eventListId1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"" + eventId1 + "\"}], \"id\": \"" + eventListId1 + "\", \"name\": \"List1\" }"))
                .andExpect(status().isOk());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPutListsByIdIfNotNameGiven() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/" + eventListId1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"1\"}], \"id\": 1}"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPutListsByIdIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"0\"}], \"id\": 500, \"name\": \"List500\" }"))
                .andExpect(status().isNotFound());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPutListsByIdWithEventThatDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"events\": [{\"id\": \"0\"}, {\"id\": \"500\"}], \"id\": 1, \"name\": \"ListNew1\" }"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canDeleteListsById() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/" + eventListId1))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotDeleteListsByIdIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/500"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetListsEventsById() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/" + eventListId1 + "/events"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostListsEventsById() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists/" + eventListId1 + "/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostListsEventsWithExistingEvent() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists/" + eventListId1 + "/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"id\": 0 }"))
                .andExpect(status().isOk());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPostListsEventsWithAnEventThatDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists" + eventListId1 + "/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"id\": 500 }"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canDeleteListsEventsById() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/" + eventListId1 + "/events/0"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotDeleteListsEventsByIdIfEventIsNotSpecified() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/" + eventListId1 + "/events"))
                .andExpect(status().isMethodNotAllowed());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotDeleteListsEventsByIdIfEventDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/" + eventListId1 + "/events/8000"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canCompareLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=" + eventListId1 + "&list2=" + eventListId2))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canCompareListsIsAssociative() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=" + eventListId2 + "&list2=" + eventListId1))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void canCompareListsIfEqual() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=" + eventListId1 + "&list2=" + eventListId1))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void userShouldNotCompareLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=1&list2=2"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void shouldNotCompareListsIfNotSpecified() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void shouldNotCompareListsIfQueriesAreMissing() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=" + eventListId1))
                .andExpect(status().isBadRequest());
    }

    @Ignore
    @WithMockUser(roles = "ADMIN")
    @DirtiesContext
    @Test
    public void shouldNotCompareListsIfOneDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=" + eventListId1 + "&list2=800"))
                .andExpect(status().isNotFound());
    }
}

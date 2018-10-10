package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListsControllerTest extends ControllerTest {
    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canPostLists() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists?name=List3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Ignore
    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canPostListsIfItExists() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists?name=List1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotPostListsIfNoNameGiven() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetListsById() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canPutListsById() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/1?name=Test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canPutListsByIdWithSameName() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/1?name=List1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotPutListsByIdIfNotNameGiven() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/500"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotPutListsByIdIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/lists/500?name=Test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canDeleteListsById() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotDeleteListsByIdIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/500"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetListsEventsById() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/1/events"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canPostListsEventsById() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists/1/events/?event_id=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canPostListsEventsByIdWithExistingId() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists/1/events/?event_id=0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotPostListsEventsByIdIfIdIsNotQueried() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/lists/1/events"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canDeleteListsEventsById() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/1/events/0"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotDeleteListsEventsByIdIfEventIsNotSpecified() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/1/events"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotDeleteListsEventsByIdIfEventDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/lists/1/events/8000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canCompareLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=1&list2=2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canCompareListsIsAssociative() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list2=2&list1=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canCompareListsIfEqual() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=1&list2=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void userShouldNotCompareLists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=1&list2=2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotCompareListsIfNotSpecified() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotCompareListsIfQueriesAreMissing() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Ignore
    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotCompareListsIfOneDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/lists/compare?list1=1&list2=800"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

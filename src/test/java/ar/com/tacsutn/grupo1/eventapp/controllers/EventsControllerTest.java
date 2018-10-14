package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventsControllerTest extends ControllerTest {
    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithAddress() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?address=argentina"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithFrom() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?from=2018-09-25T18:00:00"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithPrice() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?price=100"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithQ() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?q=something"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithTo() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?to=2018-12-16T00:00:00"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithUnknownFilterFromAPI() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?categories=entretenimiento"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetEventsWithNonExistentFilter() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/?shouldnotwork=shouldnotwork"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetTotalUsers() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/0/total_users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotGetTotalUsersIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/0/total_users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Ignore
    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotGetTotalUsersIfEventDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/8000/total_users"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetRegisteredEvents() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/registered_events"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotGetRegisteredEventsIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/registered_events"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetTotalEventsFromADateRange() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/total_events?from=1970-01-01T00:00:00&to=2070-01-01T00:00:00"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetTotalEventsFromBeginningOfTimes() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/total_events"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotGetTotalEventsIfNotAdmin() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/events/total_events"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}

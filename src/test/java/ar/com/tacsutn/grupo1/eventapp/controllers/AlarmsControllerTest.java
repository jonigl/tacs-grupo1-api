package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlarmsControllerTest extends ControllerTest {
    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetAlarm() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("alarm1"))
                .andExpect(jsonPath("$.id").value("1"));

    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotGetAlarm() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/4"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetAlarmSummary() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/today"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetAlarmSummaryDesc() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/today/?sort=desc"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canGetAlarmSummaryAsc() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/today/?sort=asc"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void canEventsAlarm() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/3/fetch"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldGetEventsWithPage() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/1/fetch/?page=1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "USER")
    @Transactional
    @DirtiesContext
    @Test
    public void shouldNotGetEventsWithPage() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/1/fetch/?page=100000000"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

}

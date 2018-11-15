package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
public class AlarmsControllerTest extends ControllerTest {

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPostAlarm() throws Exception {
        this.getMockMvc()
                .perform(post("/api/v1/alarms")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"address\": \"argentina\", \"keyword\": \"x\", \"name\": \"x\", \"price\": \"100\", \"startDateFrom\": \"2018-10-08T22:54:28.201Z\", \"startDateTo\": \"2018-10-08T22:54:28.201Z\" }"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetAlarm() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/" + alarmId1).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("alarm1"))
                .andExpect(jsonPath("$.id").value(alarmId1));

    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotGetAlarmIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/500"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canPutAlarm() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/alarms/" + alarmId1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"address\": \"argentina\", \"keyword\": \"x\", \"name\": \"x\", \"price\": \"100\", \"startDateFrom\": \"2018-10-08T22:54:28.201Z\", \"startDateTo\": \"2018-10-08T22:54:28.201Z\" }"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotPutAlarmIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(put("/api/v1/alarms/500")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"address\": \"argentina\", \"keyword\": \"x\", \"name\": \"x\", \"price\": \"100\", \"startDateFrom\": \"2018-10-08T22:54:28.201Z\", \"startDateTo\": \"2018-10-08T22:54:28.201Z\" }"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canDeleteAlarm() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/alarms/" + alarmId1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotDeleteAlarmIfNotExists() throws Exception {
        this.getMockMvc()
                .perform(delete("/api/v1/alarms/500"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canFetchAlarmPagedEvents() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/" + alarmId1 + "/fetch"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canFetchAlarmEvents() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/" + alarmId1 + "/fetch?page=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotFetchAlarmIfDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/500/fetch"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void shouldNotFetchAlarmEventsIfPageDoesNotExist() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/" + alarmId1 + "/fetch/?page=100000000"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetTodayAlarms() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/today"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetTodayAlarmsSortedDesc() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/today/?sort=desc"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DirtiesContext
    @Test
    public void canGetTodayAlarmsSortedAsc() throws Exception {
        this.getMockMvc()
                .perform(get("/api/v1/alarms/today/?sort=asc"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

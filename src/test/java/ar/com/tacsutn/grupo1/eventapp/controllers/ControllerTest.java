package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.BootstrapData;
import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.*;
import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.repositories.AuthorityRepository;
import ar.com.tacsutn.grupo1.eventapp.security.JwtTokenUtil;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import ar.com.tacsutn.grupo1.eventapp.services.SessionService;
import ar.com.tacsutn.grupo1.eventapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class ControllerTest {
    @MockBean
    private BootstrapData bootstrapData;

    @Autowired
    private AuthorityRepository authorityRepository;

    List<Authority> userAuthorities, adminAuthorities;

    @Autowired
    private UserService userService;

    private User user1, user2;

    @Autowired
    private EventService eventService;

    private EventId event1, event2;

    @Autowired
    private EventListService listService;

    @Autowired
    private AlarmService alarmService;

    private Alarm alarm1 , alarm2, alarm3;


    @MockBean
    private SessionService sessionService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    private void setAuthorities() {
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthorityName.ROLE_USER);
        authorityRepository.save(userAuthority);

        userAuthorities = Collections.singletonList(userAuthority);

        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthorityName.ROLE_ADMIN);
        authorityRepository.save(adminAuthority);

        adminAuthorities = Collections.singletonList(adminAuthority);
    }

    private void setUsers() {
        user1 = new User("JohnDoemann1", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), userAuthorities);
        user2 = new User("JanetDoemann2", "1234", "Janet", "Doemann", "janet.doemann@test.com", true, new Date(), adminAuthorities);

        userService.save(user1);
        userService.save(user2);
    }

    private void setAlarms() {

        EventFilter filter1 = new EventFilter()
                .setKeyword("nodejs")
                .setStartDateFrom(LocalDateTime.of(2018,10,2,0,0))
                .setStartDateTo(LocalDateTime.of(2018,10,3,0,0));

        EventFilter filter2 = new EventFilter()
                .setKeyword("key2")
                .setStartDateFrom(LocalDateTime.of(2018,1,1,0,0))
                .setAddress("Palestina123");

        EventFilter filter3 = new EventFilter()
                .setKeyword("key3")
                .setStartDateFrom(LocalDateTime.of(2018,10,2,0,0))
                .setStartDateTo(LocalDateTime.of(2018,10,3,0,0))
                .setAddress("Plymouth College of Art, Tavistock Place");

        alarm1 = new Alarm(user1,"alarm1",filter1);
        alarm2 = new Alarm(user2,"alarm2",filter2);
        alarm3 = new Alarm(user1,"alarm3",filter3);

        alarmService.save(alarm1);
        alarmService.save(alarm2);
        alarmService.save(alarm3);
    }

    private void setEvents() {
        event1 = new EventId("0");
        event2 = new EventId("1");

        eventService.save(event1);
        eventService.save(event2);
    }

    private void setLists() {
        EventList eventList1 = new EventList("List1", user1);
        EventList eventList2 = new EventList("List2", user1);
        EventList eventList3 = new EventList("List3", user2);

        List<EventId> list1 = new ArrayList<>(), list2 = new ArrayList<>(), list3 = new ArrayList<>();

        list1.add(event1);
        list2.add(event2);
        list3.add(event1);

        eventList1.setEvents(list1);
        eventList2.setEvents(list2);
        eventList3.setEvents(list3);

        listService.save(eventList1);
        listService.save(eventList3);
    }

    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Before
    public void before() {
        setAuthorities();
        setUsers();
        setEvents();
        setLists();
        setAlarms();

        when(sessionService.getAuthenticatedUser(any(HttpServletRequest.class))).thenReturn(user1);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Transactional
    @Test
    public void incorrectURIReturnsErrorStatus() throws Exception {
        mockMvc.perform(get("/fail")).andExpect(status().is4xxClientError());
    }
}

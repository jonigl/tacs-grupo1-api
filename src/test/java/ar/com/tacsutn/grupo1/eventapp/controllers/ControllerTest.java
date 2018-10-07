package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.BootstrapData;
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
import java.util.ArrayList;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private User user1, user2;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private EventService eventService;

    private EventId event1, event2;

    @Autowired
    private EventListService listService;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    private void setUsers() {
        Authority authority1 = new Authority();

        authority1.setName(AuthorityName.ROLE_USER);

        List<Authority> authorities = new ArrayList<>();

        authorityRepository.save(authority1);

        authorities.add(authority1);

        user1 = new User("JohnDoemann1", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), authorities);
        user2 = new User("JanetDoemann2", "1234", "Janet", "Doemann", "janet.doemann@test.com", true, new Date(), authorities);

        userService.save(user1);
        userService.save(user2);
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
        setUsers();
        setEvents();
        setLists();

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

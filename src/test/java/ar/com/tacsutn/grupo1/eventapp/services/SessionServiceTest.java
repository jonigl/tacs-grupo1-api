package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionServiceTest {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    private User user1, user2;

    @Before
    public void before() {
        createUsers();
    }

    @Ignore
    @Test
    public void canGetAuthenticatedUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        User userAuthenticated = sessionService.getAuthenticatedUser(request);
    }

    @Ignore
    @Test
    public void shouldNotGetAuthenticatedUserIfTokenIsNotProvided() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        sessionService.getAuthenticatedUser(request);
    }

    private void createUsers() {
        user1 = new User("JohnDoemann", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), null);
        user2 = new User("JohnDoemann2", "1234", "John", "Doemann", "john.doemann2@test.com", true, new Date(), null);
        userService.create(user1);
        userService.createAdmin(user2);
    }
}

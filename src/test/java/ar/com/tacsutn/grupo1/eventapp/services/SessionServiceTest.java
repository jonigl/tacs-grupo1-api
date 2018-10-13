package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.Authority;
import ar.com.tacsutn.grupo1.eventapp.models.AuthorityName;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.repositories.AuthorityRepository;
import ar.com.tacsutn.grupo1.eventapp.security.JwtAuthenticationRequest;
import ar.com.tacsutn.grupo1.eventapp.security.JwtTokenUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionServiceTest {
  @Autowired
  private SessionService sessionService;

  @Autowired
  private AuthorityRepository authorityRepository;

  List<Authority> userAuthorities, adminAuthorities;

  @Autowired
  private UserService userService;

  private User user1, user2;

  @Autowired
  @Qualifier("jwtUserDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Before
  public void before() {
    setAuthorities();
    setUsers();
  }

  @Test
  public void canGetAuthenticatedUser() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(user1.getUsername(), user1.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);
    request.addHeader("Authorization", "Value= " + token);

    User userAuthenticated = sessionService.getAuthenticatedUser(request);
    Assert.assertEquals(user1, userAuthenticated);
  }

  private void setUsers() {
    user1 = new User("JohnDoemann1", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), userAuthorities);
    user2 = new User("JanetDoemann2", "1234", "Janet", "Doemann", "janet.doemann@test.com", true, new Date(), adminAuthorities);

    userService.save(user1);
    userService.save(user2);
  }


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
}

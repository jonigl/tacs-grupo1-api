package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
  @Autowired
  private UserService userService;
  private User user1;

  @Before
  public void before() {
    createUsers();
  }

  @Test
  public void canGetUserById() {
    User result = userService.getById(user1.getId()).orElseThrow(NoSuchElementException::new);
    assertEquals(result.getId(), user1.getId());
  }

  @Test
  public void canAccessPropertiesOfUser() {
    User result = userService.getById(user1.getId()).orElseThrow(NoSuchElementException::new);
    assertEquals(result.getLastAccess(), user1.getLastAccess());
  }

  @Test(expected = NoSuchElementException.class)
  public void getExceptionWhenUserIsNotFound() {
    userService.getById(user1.getId() + 123).orElseThrow(NoSuchElementException::new);
  }

  private void createUsers() {
    this.user1 = new User("JohnDoemann2", "1234", "John", "Doemann", "john.doemann2@test.com", true, new Date(), null);
    this.userService.save(this.user1);
  }
}

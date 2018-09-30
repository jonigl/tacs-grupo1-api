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
  private User user1, user2;

  @Before
  public void before() {
    createUsers();
  }

  @Test
  public void canCreateUser() {
      User user3 = new User("JaneDoemann", "1234", "Jane", "Doemann", "jane.doemann@test.com", true, new Date(), null);
      userService.create(user3);
      User result = userService.getById(user3.getId()).orElseThrow(NoSuchElementException::new);
      assertEquals(result.getId(), user3.getId());
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

  @Test
  public void canUpdateUser() {
      User result = userService.getById(user1.getId()).orElseThrow(NoSuchElementException::new);
      assertEquals(result.getFirstname(), user1.getFirstname());
      result.setFirstname("Johnny");
      userService.save(result);
      User resultReload = userService.getById(user1.getId()).orElseThrow(NoSuchElementException::new);
      assertEquals(resultReload.getFirstname(), "Johnny");
  }

  private void createUsers() {
    user1 = new User("JohnDoemann", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), null);
    user2 = new User("JohnDoemann2", "1234", "John", "Doemann", "john.doemann2@test.com", true, new Date(), null);
    userService.create(user1);
    userService.createAdmin(user2);
  }
}

package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AlarmServiceTest {
  @Autowired
  private UserService userService;
  @Autowired
  private AlarmService alarmService;
  private User user1, user2;
  private EventFilter eventFilter;

  @Before
  public void before() {
    createEventFilters();
    createUsers();
    createAlarms();
  }

  @Test
  public void shouldContainOneAlarm() {
    assertEquals((long) this.alarmService.getTotalAlarmsByUserId(user1.getId()), 1);
  }

  private void createUsers() {
    this.user1 = new User("JohnDoemann", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), null);
    this.user2 = new User("JanetDoemann2", "1234", "Janet", "Doemann", "janet.doemann@test.com", true, new Date(), null);
    this.userService.save(this.user1);
    this.userService.save(this.user2);
  }

  private void createEventFilters() {
    this.eventFilter = new EventFilter();
    this.eventFilter.setKeyword("Pop");
  }

  private void createAlarms() {
    Alarm alarm1 = new Alarm(this.user1, "Alarm", this.eventFilter);
    Alarm alarm2 = new Alarm(this.user2, "Alarm2", this.eventFilter);
    this.alarmService.save(alarm1);
    this.alarmService.save(alarm2);
  }
}

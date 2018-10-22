package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.models.Alarm;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

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
    private Alarm alarm1, alarm2;

    @Before
    public void before() {
        createEventFilters();
        createUsers();
        createAlarms();
    }

    @Test
    public void shouldContainOneAlarm() {
        assertEquals(this.alarmService.getTotalAlarmsByUserId(user1.getId()), 1);
    }

    @Test
    public void canGetAlarmById() {
        Alarm result = alarmService.getById(alarm2.getId()).orElseThrow(NoSuchElementException::new);
        assertEquals(result.getId(), alarm2.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void canNotGetAlarmFromAnotherUser() {
        alarmService.getById(user1, alarm2.getId()).orElseThrow(NoSuchElementException::new);
    }

    @Test(expected = NoSuchElementException.class)
    public void getExceptionWhenAlarmIsNotFound () {
        userService.getById(alarm1.getId() + 123).orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void canRemoveAlarm() {
        assertEquals(1, (long) alarmService.getTotalAlarmsByUserId(user1.getId()));

        PageRequest pageRequest = PageRequest.of(0, 50);
        Page<Alarm> alarms = alarmService.getAllAlarmsByUserId(user1.getId(), pageRequest);

        assertEquals(1, alarms.getTotalElements());
        alarms.forEach(alarmService::remove);

        assertEquals(0, (long) alarmService.getTotalAlarmsByUserId(user1.getId()));

        Page<Alarm> alarmsReload = alarmService.getAllAlarmsByUserId(user1.getId(), pageRequest);

        assertEquals( 0, alarmsReload.getTotalElements());
    }

    private void createUsers() {
        user1 = new User("JohnDoemann", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), null);
        user2 = new User("JanetDoemann2", "1234", "Janet", "Doemann", "janet.doemann@test.com", true, new Date(), null);
        userService.create(user1);
        userService.create(user2);
    }

    private void createEventFilters() {
        eventFilter = new EventFilter();
        eventFilter.setKeyword("Pop");
    }

    private void createAlarms() {
        alarm1 = new Alarm(user1, "Alarm", eventFilter);
        alarm2 = new Alarm(user2, "Alarm2", eventFilter);
        alarmService.save(alarm1);
        alarmService.save(alarm2);
    }
}

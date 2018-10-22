package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.BootstrapData;
import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {
    @MockBean
    private BootstrapData bootstrapData;

    @Mock
    private User user1;

    @Autowired
    private EventService eventService;

    private EventId event1, event2, event3;

    private EventList eventList;

    @Before
    public void before() {
        setEvents();
        setEventList();
    }

    @Transactional
    @Test
    public void canGetById() {
        EventId actualEvent = eventService.getById(event1.getId()).orElseThrow(NoSuchElementException::new);

        assertEquals(event1, actualEvent);
    }

    @Transactional
    @Test
    public void shouldNotGetEventIfNotExists() {
        assertFalse(eventService.getById(event2.getId()).isPresent());
    }

    @Transactional
    @Test
    public void canDeleteEventFromList() {
        eventService.removeEvent(eventList, event1);

        assertArrayEquals(new EventId[]{}, eventList.getEvents().toArray());
    }

    @Transactional
    @Test
    public void shouldNotDeleteEventIfNotExists() {
        eventService.removeEvent(eventList, event2);

        assertEquals(1, eventList.getEvents().size());
    }

    @Transactional
    @Test
    public void canFindAllEventsBetweenDates() {
        addAllEvents();

        Date from = dateGeneratorFromToday(-1);
        Date to = dateGeneratorFromToday(1);

        long eventsCount = eventService.getTotalEventsBetween(from, to);

        assertEquals(3, eventsCount);
    }

    @Transactional
    @Test
    public void shouldNotFindEventsIfNotInRange() {
        addAllEvents();

        Date from = dateGeneratorFromToday(100);
        Date to = dateGeneratorFromToday(1000);

        long eventsCount = eventService.getTotalEventsBetween(from, to);

        assertEquals(0, eventsCount);
    }

    private void setEvents() {
        event1 = new EventId("1");
        event2 = new EventId("2");
        event3 = new EventId("3");

        eventService.save(event1);
    }

    private void setEventList() {
        List<EventId> events = new ArrayList<>();

        events.add(event1);

        eventList = new EventList("Test", user1);

        eventList.setEvents(events);
    }

    private void addAllEvents() {
        eventService.save(event2);
        eventService.save(event3);
    }

    private Date dateGeneratorFromToday(long days) {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(days));
    }
}

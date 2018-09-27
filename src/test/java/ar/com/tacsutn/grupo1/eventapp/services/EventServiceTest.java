package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EventServiceTest {
    @Mock
    private User user1;

    @Autowired
    private EventService eventService;

    private EventId event1, event2, event3;

    private EventList eventList;

    @Before
    public void before() {
        event1 = new EventId("1", dateGenerator(1000));
        event2 = new EventId("2", dateGenerator(2000));
        event3 = new EventId("3", dateGenerator(3000));

        eventService.save(event1);

        List<EventId> events = new ArrayList<>();

        events.add(event1);

        eventList = new EventList("Test", user1);

        eventList.setEvents(events);
    }

    @Test
    public void canGetById() {
        EventId actualEvent = eventService.getById(event1.getId()).orElseThrow(NoSuchElementException::new);

        assertEquals(event1, actualEvent);
    }

    @Test
    public void shouldNotGetEventIfNotExists() {
        assertFalse(eventService.getById(event2.getId()).isPresent());
    }

    @Test
    public void canDeleteEventFromList() {
        eventService.removeEvent(eventList, event1);

        assertArrayEquals(new EventId[]{}, eventList.getEvents().toArray());
    }

    @Test
    public void shouldNotDeleteEventIfNotExists() {
        eventService.removeEvent(eventList, event2);

        assertEquals(1, eventList.getSize());
    }

    @Test
    public void canFindAllEventsBetweenDates() {
        addAllEvents();

        Date from = dateGenerator(1000);
        Date to = dateGenerator(3000);

        long eventsCount = eventService.getTotalEventsBetween(from, to);

        assertEquals(3, eventsCount);
    }

    @Test
    public void canFindSomeEventsAfterFromDate() {
        addAllEvents();

        Date from = dateGenerator(2000);
        Date to = dateGenerator(3000);

        long eventsCount = eventService.getTotalEventsBetween(from, to);

        assertEquals(2, eventsCount);
    }

    @Test
    public void canFindSomeEventsBeforeToDate() {
        addAllEvents();

        Date from = dateGenerator(1000);
        Date to = dateGenerator(2000);

        long eventsCount = eventService.getTotalEventsBetween(from, to);

        assertEquals(2, eventsCount);
    }

    @Test
    public void shouldNotFindEventsIfNotInRange() {
        addAllEvents();

        Date from = dateGenerator(10000);
        Date to = dateGenerator(30000);

        long eventsCount = eventService.getTotalEventsBetween(from, to);

        assertEquals(0, eventsCount);
    }

    private Date dateGenerator(long milliseconds) {
        return new Date(milliseconds);
    }

    private void addAllEvents() {
        eventService.save(event2);
        eventService.save(event3);
    }
}

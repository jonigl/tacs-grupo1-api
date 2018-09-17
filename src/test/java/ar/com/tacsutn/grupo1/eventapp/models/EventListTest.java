package ar.com.tacsutn.grupo1.eventapp.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EventListTest {
    private User mockUser1, mockUser2;
    private Event event1, event2;
    private List<Event> events1, events2;
    private EventList list1, list2;

    @Before
    public void before() {
        mockUser1 = mockUser2 = mock(User.class);

        event1 = new Event("0", "Event 1", "Null");
        event2 = new Event("1", "Event 2", "Null");

        events1 = new ArrayList<Event>();
        events2 = new ArrayList<Event>();

        events1.add(event1);
        events1.add(event2);

        events2.add(event2);

        list1 = new EventList(0, "List 1", mockUser1, events1);
        list2 = new EventList(1, "List 2", mockUser2, events2);
    }

    @Test
    public void shouldBeTrueIfUserHasEvent() {
        assertTrue(list1.hasEvent(event1));
    }

    @Test
    public void shouldBeFalseIfUserHasNotEvent() {
        assertFalse(list2.hasEvent(event1));
    }

    @Test
    public void canGetCommonEventsFromAnotherEventList() {
        List<Event> expected = Arrays.asList(event2);

        List<Event> comparison = list1.getCommonEvents(list2);

        assertArrayEquals(expected.toArray(), comparison.toArray());
    }

    @Test
    public void getCommonEventsIsAssociative() {
        List<Event> expected = Arrays.asList(event2);

        List<Event> comparison = list2.getCommonEvents(list1);

        assertArrayEquals(expected.toArray(), comparison.toArray());
    }

    @Test
    public void shouldNotFindCommonEventsIfThereAreNot() {
        list1.removeEvent(event2);

        List<Event> comparison = list2.getCommonEvents(list1);

        assertEquals(0, comparison.size());
    }
}

package ar.com.tacsutn.grupo1.eventapp.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventListTest {
    private List<Event> events;

    private EventList eventList;

    private User user;

    @Before
    public void before() {
        events = new ArrayList<Event>();

        eventList = new EventList(new Long(1), "Test", events);

        user = new User(new Long(1), "Test User", "1234", Role.NORMAL);

        user.addList(eventList);
    }

    @Test
    public void shouldCreateAnEventList() {
        assertEquals(eventList, user.getLists().get(0));
    }

    @Ignore
    @Test
    public void shouldNotCreateAnEventListIfExists() {

    }

    @Test
    public void shouldDeleteAnEventList() {
        user.deleteList(new Long(1));

        assertEquals(0, user.getLists().size());
    }

    @Ignore
    @Test
    public void shouldNotDeleteAnEventListIfNotExists() {

    }

    @Test
    public void shouldChangeListName() {
        user.changeListName(new Long(1), "New name");

        assertEquals("New name", eventList.getName());
    }
}
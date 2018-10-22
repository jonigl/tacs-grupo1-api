package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.BootstrapData;
import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventListServiceTest {
    @MockBean
    private BootstrapData bootstrapData;

    @Autowired
    private UserService userService;

    private User user1, user2;

    @Autowired
    private EventService eventService;

    private EventId event1, event2;

    @Autowired
    private EventListService listService;

    private EventList eventList1, eventList2, eventList3;

    @Before
    public void before() {
        setUsers();
        setEvents();
        setLists();
    }

    @DirtiesContext
    @Test
    public void canGetEventsList() {
        EventList result = listService.getById(eventList1.getId()).orElseThrow(NoSuchElementException::new);

        assertEquals(eventList1.getId(), result.getId());
    }

    @DirtiesContext
    @Test
    public void shouldNotGetEventsListIfNotExists() {
        assertFalse(listService.getById("-500").isPresent());
    }

    @DirtiesContext
    @Test
    public void canGetAllEventLists() {
        assertEquals(2, listService.getLists().getContent().size());
    }

    @DirtiesContext
    @Test
    public void canGetAllEventListsForAUser() {
        assertEquals(1, listService.getListsByUser(user1).getContent().size());
    }

    @DirtiesContext
    @Test
    public void canAddEventList() {
        listService.save(eventList2);

        assertEquals(2L, eventsCount(user1));
    }

    @DirtiesContext
    @Test
    public void shouldNotAddEventListIfExists() {
        listService.save(eventList1);

        assertEquals(1L, eventsCount(user1));
    }

    @DirtiesContext
    @Test
    public void canDeleteEventList() {
        listService.delete(user1, eventList1.getId());

        assertEquals(0L, eventsCount(user1));
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotDeleteListIfNotExists() {
        listService.delete(user1, "-2000");
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotDeleteListIfNotOwner() {
        listService.delete(user1, eventList3.getId());
    }

    @DirtiesContext
    @Test
    public void canChangeEventListName() {
        listService.rename(user1, eventList1.getId(), "TestRename");

        EventList renamedList = listService.getById(eventList1.getId()).orElseThrow(NoSuchElementException::new);

        assertEquals("TestRename", renamedList.getName());
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotChangeListNameIfNotExists() {
        listService.rename(user1, "-2000", "TestRename");
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotChangeListNameIfNotOwner() {
        listService.rename(user1, eventList3.getId(), "TestRename");
    }

    @DirtiesContext
    @Test
    @Ignore
    public void canGetCommonEventsFromTwoDifferentEventList() {
        List<Event> commonEvents = getCommonEvents(eventList1, eventList3);

        assertEquals(1, commonEvents.size());
    }

    @DirtiesContext
    @Test
    @Ignore
    public void gettingCommonEventsIsAssociative() {
        List<Event> commonEvents = getCommonEvents(eventList3, eventList1);

        assertEquals(event1.getId(), commonEvents.get(0).getId());
    }

    @DirtiesContext
    @Test
    @Ignore
    public void canGetCommonEventsEqualToAllIfSameEventList() {
        List<Event> commonEvents = getCommonEvents(eventList1, eventList1);

        assertEquals(eventList1.getEvents().size(), commonEvents.size());
    }

    @DirtiesContext
    @Test
    public void shouldNotFindCommonEventsIfThereAreNoCommonEvents() {
        listService.save(eventList2);

        List<Event> commonEvents = getCommonEvents(eventList1, eventList2);

        assertTrue(commonEvents.isEmpty());
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindCommonEventsIfFirstIdDoesNotExist() {
        getCommonEvents("-2000", eventList1.getId());
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindCommonEventsIfSecondIdDoesNotExist() {
        getCommonEvents(eventList1.getId(), "-4000");
    }

    @DirtiesContext
    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindCommonEventsIfNeitherIdsDoesNotExist() {
        getCommonEvents("-2000", "-4000");
    }

    @DirtiesContext
    @Test
    public void canFindUserCountInterestedInEvent() {
        long interestedCount = eventService.getTotalUsersByEventId("0");

        assertEquals(2L, interestedCount);
    }

    @DirtiesContext
    @Test
    public void shouldBe0IfNoUsersAreInterestedInEvent() {
        long interestedCount = eventService.getTotalUsersByEventId("foo");

        assertEquals(0L, interestedCount);
    }

    private void setUsers() {
        user1 = new User("JohnDoemann1", "1234", "John", "Doemann", "john.doemann@test.com", true, new Date(), null);
        user2 = new User("JanetDoemann2", "1234", "Janet", "Doemann", "janet.doemann@test.com", true, new Date(), null);

        userService.save(user1);
        userService.save(user2);
    }

    private void setEvents() {
        event1 = new EventId("0");
        event2 = new EventId("1");

        eventService.save(event1);
        eventService.save(event2);
    }

    private void setLists() {
        eventList1 = new EventList("Test1", user1);
        eventList2 = new EventList("Test2", user1);
        eventList3 = new EventList("Test3", user2);

        List<EventId> list1 = new ArrayList<>(), list2 = new ArrayList<>(), list3 = new ArrayList<>();

        list1.add(event1);
        list2.add(event2);
        list3.add(event1);

        eventList1.setEvents(list1);
        eventList2.setEvents(list2);
        eventList3.setEvents(list3);

        listService.save(eventList1);
        listService.save(eventList3);
    }

    private long eventsCount(User user) {
        return listService.getTotalEventListByUserId(user.getId());
    }

    private List<Event> getCommonEvents(EventList eventList1, EventList eventList2) {
        return listService.getCommonEvents(eventList1.getId(), eventList2.getId(), PageRequest.of(0, 20)).getContent();
    }

    private List<Event> getCommonEvents(String id1, String id2) {
        return listService.getCommonEvents(id1, id2, PageRequest.of(0, 20)).getContent();
    }
}

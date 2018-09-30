package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class EventListServiceTest {
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

    @Test
    public void canGetEventsList() {
        EventList result = listService.getById(eventList1.getId()).orElseThrow(NoSuchElementException::new);

        assertEquals(eventList1.getId(), result.getId());
    }

    @Test
    public void shouldNotGetEventsListIfNotExists() {
        assertFalse(listService.getById(-500L).isPresent());
    }

    @Ignore
    @Test
    public void canGetAllEventLists() {
        listService.getLists().getContent().iterator().forEachRemaining(e -> System.out.println(e.getName()));

        assertArrayEquals(new EventList[]{eventList1, eventList3}, listService.getLists().getContent().toArray());
    }

    @Test
    public void canGetAllEventListsForAUser() {
        assertArrayEquals(new EventList[]{eventList1}, listService.getListsByUser(user1).getContent().toArray());
    }

    @Test
    public void canAddEventList() {
        listService.save(eventList2);

        assertEquals(2L, eventsCount(user1));
    }

    @Test
    public void shouldNotAddEventListIfExists() {
        listService.save(eventList1);

        assertEquals(1L, eventsCount(user1));
    }

    @Test
    public void canDeleteEventList() {
        listService.delete(user1, eventList1.getId());

        assertEquals(0L, eventsCount(user1));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDeleteListIfNotExists() {
        listService.delete(user1, -2000L);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDeleteListIfNotOwner() {
        listService.delete(user1, eventList3.getId());
    }

    @Test
    public void canChangeEventListName() {
        listService.rename(user1, eventList1.getId(), "TestRename");

        EventList renamedList = listService.getById(eventList1.getId()).orElseThrow(NoSuchElementException::new);

        assertEquals("TestRename", renamedList.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotChangeListNameIfNotExists() {
        listService.rename(user1, -2000L, "TestRename");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotChangeListNameIfNotOwner() {
        listService.rename(user1, eventList3.getId(), "TestRename");
    }

    @Test
    public void canGetCommonEventsFromTwoDifferentEventList() {
        List<EventId> commonEvents = getCommonEvents(eventList1, eventList3);

        assertArrayEquals(new EventId[]{event1}, commonEvents.toArray());
    }

    @Test
    public void gettingCommonEventsIsAssociative() {
        List<EventId> commonEvents = getCommonEvents(eventList3, eventList1);

        assertArrayEquals(new EventId[]{event1}, commonEvents.toArray());
    }

    @Test
    public void canGetCommonEventsEqualToAllIfSameEventList() {
        List<EventId> commonEvents = getCommonEvents(eventList1, eventList1);

        assertArrayEquals(eventList1.getEvents().toArray(), commonEvents.toArray());
    }

    @Test
    public void shouldNotFindCommonEventsIfThereAreNoCommonEvents() {
        listService.save(eventList2);

        List<EventId> commonEvents = getCommonEvents(eventList1, eventList2);

        assertArrayEquals(new EventId[]{}, commonEvents.toArray());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindCommonEventsIfFirstIdDoesNotExist() {
        getCommonEvents(-2000L, eventList1.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindCommonEventsIfSecondIdDoesNotExist() {
        getCommonEvents(eventList1.getId(), -4000L);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindCommonEventsIfNeitherIdsDoesNotExist() {
        getCommonEvents(-2000L, -4000L);
    }

    @Test
    public void canFindUserCountInterestedInEvent() {
        int interestedCount = eventService.getTotalUsersByEventId("0");

        assertEquals(2, interestedCount);
    }

    @Test
    public void shouldBe0IfNoUsersAreInterestedInEvent() {
        int interestedCount = eventService.getTotalUsersByEventId("foo");

        assertEquals(0, interestedCount);
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

    private List<EventId> getCommonEvents(EventList eventList1, EventList eventList2) {
        return listService.getCommonEvents(eventList1.getId(), eventList2.getId()).getContent();
    }

    private List<EventId> getCommonEvents(Long id1, Long id2) {
        return listService.getCommonEvents(id1, id2).getContent();
    }
}

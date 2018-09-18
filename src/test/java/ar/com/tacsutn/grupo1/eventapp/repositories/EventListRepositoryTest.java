package ar.com.tacsutn.grupo1.eventapp.repositories;

//import ar.com.tacsutn.grupo1.eventapp.models.EventList;
//import ar.com.tacsutn.grupo1.eventapp.models.User;
//import ar.com.tacsutn.grupo1.eventapp.models.exceptions.NotListOwnerException;
//import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class EventListRepositoryTest {
//
//    private User mockedUser1;
//    private User mockedUser2;
//
//    @Autowired
//    private EventListService listService;
//
//    @Before
//    public void before() {
//        mockedUser1 = mock(User.class);
//        mockedUser2 = mock(User.class);
//
//        listService.save(mockedUser1, "Test");
//    }
//
//    @Test
//    public void canAddEventList() {
//        EventList list = listService.save(mockedUser1, "New Test");
//        EventList savedList = listService.getById(list.getId()).getById();
//
//        assertEquals("New Test", savedList.getName());
//    }
//
//    @Ignore
//    @Test
//    public void shouldNotAddEventListIfExists() {
//        listService.save(mockedUser1, "Test");
//    }
//
//    @Test
//    public void canDeleteEventList() {
//        listService.delete(mockedUser1, 0L);
//
//        assertEquals(0, listService.getLists().getTotalElements());
//    }
//
//    @Test(expected = NotListOwnerException.class)
//    public void shouldNotDeleteOtherEventList() {
//        listService.delete(mockedUser2, 0);
//    }
//
//    @Ignore
//    @Test
//    public void shouldNotDeleteEventListIfNotExists() {
//        listService.delete(mockedUser1, 1);
//    }
//
//    @Test
//    public void canChangeEventListName() {
//        listService.rename(mockedUser1, 0L, "New Name");
//        EventList renamedList = listService.getById(0L).getById();
//
//        assertEquals("New Name", renamedList.getName());
//    }
//
//    @Test(expected = NotListOwnerException.class)
//    public void shouldNotChangeOtherEventListName() {
//        listService.rename(mockedUser2, 0, "New Name");
//    }
//
//    @Ignore
//    @Test
//    public void shouldNotChangeEventListNameIfNotExists() {
//
//    }
//
//    @Test
//    public void canGetCommonEventsFromTwoUsers() {
//
//    }
//}

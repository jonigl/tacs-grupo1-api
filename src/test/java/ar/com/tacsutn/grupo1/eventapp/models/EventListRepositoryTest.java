package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.models.exceptions.NotListOwnerException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EventListRepositoryTest {
    User mockedUser1, mockedUser2;
    ListsRepository repository;

    @Before
    public void before() {
        mockedUser1 = mock(User.class);
        mockedUser2 = mock(User.class);

        repository = new ListsRepository(new ArrayList<EventList>(), 0);

        repository.create(mockedUser1, "Test");
    }

    @Test
    public void canAddEventList() {
        repository.create(mockedUser1, "New Test");

        assertEquals("New Test", repository.getListName(1));
    }

    @Ignore
    @Test
    public void shouldNotAddEventListIfExists() {
        repository.create(mockedUser1, "Test");
    }

    @Test
    public void canDeleteEventList() {
        repository.delete(mockedUser1, 0);

        assertEquals(0, repository.getListsCount());
    }

    @Test(expected = NotListOwnerException.class)
    public void shouldNotDeleteOtherEventList() {
        repository.delete(mockedUser2, 0);
    }

    @Ignore
    @Test
    public void shouldNotDeleteEventListIfNotExists() {
        repository.delete(mockedUser1, 1);
    }

    @Test
    public void canChangeEventListName() {
        repository.changeName(mockedUser1, 0, "New Name");

        assertEquals("New Name", repository.getListName(0));
    }

    @Test(expected = NotListOwnerException.class)
    public void shouldNotChangeOtherEventListName() {
        repository.changeName(mockedUser2, 0, "New Name");
    }

    @Ignore
    @Test
    public void shouldNotChangeEventListNameIfNotExists() {

    }
}

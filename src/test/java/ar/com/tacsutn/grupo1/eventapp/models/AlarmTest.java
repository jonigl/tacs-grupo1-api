package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.repositories.EventsRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AlarmTest {
    private EventsRepository mockRepository;
    private List<Event> mockEvents;
    private Criteria mockCriteria;
    private Alarm alarm;

    @Before
    public void before() {
        mockRepository = mock(EventsRepository.class);
        mockCriteria = mock(Criteria.class);
        mockEvents = mock(List.class);

        when(mockRepository.search(mockCriteria)).thenReturn(mockEvents);
        when(mockEvents.size()).thenReturn(100);

        Date date = new Date(new Date().getTime() + 500);

        alarm = new Alarm(0, "Test", mockCriteria, date, mockRepository);
    }

    @Test
    public void canSearchInRepository() {
        assertEquals(0, alarm.getEventsCount());

        try {
            sleep(1000);
        }
        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals(100, alarm.getEventsCount());
    }

    @Test
    public void shouldNotReturnEventsWhenNotCalledYet() {
        assertEquals(0, alarm.getEventsCount());
    }
}

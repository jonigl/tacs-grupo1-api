package ar.com.tacsutn.grupo1.eventapp.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CriteriaTest {
    private Criteria criteria;
    private Event eventMock1, eventMock2;

    @Before
    public void before() {
        eventMock1 = mock(Event.class);
        eventMock2 = mock(Event.class);

        when(eventMock1.getName()).thenReturn("Test");
        when(eventMock2.getName()).thenReturn("Fail");

        criteria = new EqualNameCriteria("Test");
    }

    @Test
    public void EqualNameCriteriaTrueWhenSameName() {
        assertTrue(criteria.satisfies(eventMock1));
    }

    @Test
    public void EqualNameCriteriaFalseWhenDifferentName() {
        assertFalse(criteria.satisfies(eventMock2));
    }
}

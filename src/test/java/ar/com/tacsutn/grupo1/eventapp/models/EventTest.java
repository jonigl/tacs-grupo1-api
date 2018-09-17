package ar.com.tacsutn.grupo1.eventapp.models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventTest {
    private Event event;
    private LocalDateTime dateBefore, dateAfter;

    @Before
    public void before() {
        event = new Event("0", "Event 1", "Null");
    }

    @Test
    public void shouldBeTrueIfDateIsInRange() {
        dateBefore = LocalDateTime.now().minusDays(5);
        dateAfter = LocalDateTime.now().plusDays(5);

        assertTrue(event.isInDateRange(dateBefore, dateAfter));
    }

    @Test
    public void shouldBeFalseIfDateIsBelowRange() {
        dateBefore = LocalDateTime.now().plusDays(5);
        dateAfter = LocalDateTime.now().plusDays(10);

        assertFalse(event.isInDateRange(dateBefore, dateAfter));
    }

    @Test
    public void shouldBeTrueIfDateIsOverRange() {
        dateBefore = LocalDateTime.now().minusDays(5);
        dateAfter = LocalDateTime.now().minusDays(10);

        assertFalse(event.isInDateRange(dateBefore, dateAfter));
    }
}

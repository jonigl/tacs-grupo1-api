package ar.com.tacsutn.grupo1.eventapp.web;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(EventsController.class)
public class EventsControllerTest extends ControllerTest {
    @Test
    public void getEventsReturnsOkStatus() throws Exception {
        test200Response("/api/v1/events?find=last");
    }

    @Test
    public void getEventsReturnsEventsMatchingCriteria() throws Exception {
        testRoute("/api/v1/events?find=last", "El evento 10 cumple con el criterio 'last'.");
    }

    @Test
    public void getTotalUsersReturnsOkStatus() throws Exception {
        test200Response("/api/v1/events/100/total_users");
    }

    @Test
    public void getTotalUsersReturnsInterestedCountInEvent() throws Exception {
        testRoute("/api/v1/events/100/total_users","10 usuarios estan interesados en el evento 100.");
    }

    @Test
    public void getTotalEventsReturnsOkStatus() throws Exception {
        test200Response("/api/v1/events/total_events");
    }

    @Test
    public void getTotalEventsReturnsEventsFromBeginningToEnd() throws Exception {
        testRoute("/api/v1/events/total_events", "Registrados 100 eventos desde el inicio de los tiempos hasta el dia de hoy.");
    }

    @Test
    public void getTotalEventsReturnsEventsFromADateToEnd() throws Exception {
        testRoute("/api/v1/events/total_events?from=2014-12-03T10:15:30", "Registrados 100 eventos desde 2014-12-03 hasta el dia de hoy.");
    }

    @Test
    public void getTotalEventsReturnsEventsFromBeginningToDate() throws Exception {
        testRoute("/api/v1/events/total_events?to=2014-12-03T10:15:30", "Registrados 100 eventos desde el inicio de los tiempos hasta 2014-12-03.");
    }

    @Test
    public void getTotalEventsReturnsEventsFromDateToAnotherDate() throws Exception {
        testRoute("/api/v1/events/total_events?from=2013-12-03T10:15:30&to=2014-12-03T10:15:30", "Registrados 100 eventos desde 2013-12-03 hasta 2014-12-03.");
    }
}

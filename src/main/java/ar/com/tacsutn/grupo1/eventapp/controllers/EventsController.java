package ar.com.tacsutn.grupo1.eventapp.controllers;

import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Events", description = "Events resources")
public class EventsController {
    @GetMapping("/events")
    public MockupResponse getEvent(@RequestParam(value = "find") String criteria) {
        // TODO: Add logic to get an Event given a certain filter

        return new MockupResponse("El evento 10 cumple con el criterio '" + criteria + "'.");
    }

    @GetMapping("/events/{event_id}/total_users")
    public MockupResponse getTotalUsers(@PathVariable Long event_id) {

        // TODO: Add logic to get the number of users interested in an event

        return new MockupResponse("10 usuarios estan interesados en el evento " + event_id.toString() + ".");
    }

    @GetMapping("/events/total_events")
    public MockupResponse getTotalEvents(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(value = "from", required = false) LocalDateTime from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(value = "to", required = false) LocalDateTime to) {

        // TODO: Add logic to get the number of events between a date range

        String fromString = (from != null) ? from.format(DateTimeFormatter.ISO_LOCAL_DATE) : "el inicio de los tiempos";
        String toString = (to != null) ? to.format(DateTimeFormatter.ISO_LOCAL_DATE) : "el dia de hoy";

        return new MockupResponse("Registrados 100 eventos desde " + fromString + " hasta " + toString + ".");
    }
}

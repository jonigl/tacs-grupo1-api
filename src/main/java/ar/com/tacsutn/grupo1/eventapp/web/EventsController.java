package ar.com.tacsutn.grupo1.eventapp.web;

import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Events", description = "Events resources")
public class EventsController {
    @GetMapping("/events/{event_id}/total_users")
    public MockupResponse getTotalUsers(@PathVariable Long event_id) {

        // TODO: Add logic

        return new MockupResponse("10 usuarios estan interesados en el evento " + event_id.toString() + ".");
    }

    @GetMapping("/events/total_events")
    public MockupResponse getTotalEvents(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(value = "from", required = false) LocalDateTime from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(value = "to", required = false) LocalDateTime to) {

        // TODO: Add logic

        String fromString = (from != null) ? from.format(DateTimeFormatter.ISO_LOCAL_DATE) : "el inicio de los tiempos";
        String toString = (to != null) ? to.format(DateTimeFormatter.ISO_LOCAL_DATE) : "el dia de hoy";

        return new MockupResponse("Registrados 100 eventos desde " + fromString + " hasta " + toString + ".");
    }
}

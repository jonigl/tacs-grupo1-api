package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Events", description = "Events resources")
public class EventsController {

    private final EventbriteClient eventbriteClient;
    private final EventService eventService;

    @Autowired
    public EventsController(
            EventbriteClient eventbriteClient,
            EventService eventService) {

        this.eventbriteClient = eventbriteClient;
        this.eventService = eventService;
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('USER')")
    public RestPage<Event> getEvent(
            @RequestParam(value = "q", required = false)
            String keyboard,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "from", required = false)
            LocalDateTime from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "to", required = false)
            LocalDateTime to,
            @RequestParam(value = "address", required = false)
            String address,
            @RequestParam(value = "price", required = false)
            String price,
            @RequestParam(value = "page", required = false, defaultValue = "1")
            Integer page
    ) {
        EventFilter eventFilter = new EventFilter()
                .setKeyword(keyboard)
                .setStartDateFrom(from)
                .setStartDateTo(to)
                .setAddress(address)
                .setPrice(price);

        return eventbriteClient.searchEvents(eventFilter, page)
                .orElseThrow(() -> new ResourceNotFoundException("Events not found."));
    }

    @GetMapping("/events/{event_id}/total_users")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Integer> getTotalUsers(@PathVariable String event_id) {
        Map<String, Integer> response = new HashMap<>();
        response.put("total_users", eventService.getTotalUsersByEventId(event_id));
        return response;
    }

    @GetMapping("/events/total_events")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> getTotalEvents(

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "from")
            Optional<Date> from,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "to")
            Optional<Date> to) {

        Map<String, Long> response = new HashMap<>();
        Long total = eventService.getTotalEventsBetween(
            from.orElse(new Date(0)),
            to.orElse(new Date(Long.MAX_VALUE))
        );

        response.put("total_events", total);
        return response;
    }
}

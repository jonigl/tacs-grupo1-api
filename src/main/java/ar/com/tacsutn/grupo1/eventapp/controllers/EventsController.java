package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Events", description = "Events resources")
public class EventsController {

    private final EventbriteClient eventbriteClient;
    private final EventListService eventListService;
    private final EventService eventService;

    @Autowired
    public EventsController(EventbriteClient eventbriteClient, EventListService eventListService, EventService eventService) {
        this.eventbriteClient = eventbriteClient;
        this.eventListService = eventListService;
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

        return eventbriteClient.searchEvents(eventFilter, page).orElseThrow(() -> new ResourceNotFoundException("Events not found."));
    }

    @GetMapping("/events/{event_id}/total_users")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String,Integer> getTotalUsers(@PathVariable String event_id) {
        Map<String,Integer> response = new HashMap<>();
        response.put("total_users", eventService.getTotalUsersByEventId(event_id));
        return response;
    }

    @GetMapping("/events/total_events")
    @PreAuthorize("hasRole('ADMIN')")
    public MockupResponse getTotalEvents(

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "from", required = false)
            LocalDateTime from,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "to", required = false)
            LocalDateTime to) {

        // TODO: Add logic to getById the number of events between a date range

        String fromString = (from != null) ? from.format(DateTimeFormatter.ISO_LOCAL_DATE) : "el inicio de los tiempos";
        String toString = (to != null) ? to.format(DateTimeFormatter.ISO_LOCAL_DATE) : "el dia de hoy";

        return new MockupResponse("Registrados 100 eventos desde " + fromString + " hasta " + toString + ".");
    }
}

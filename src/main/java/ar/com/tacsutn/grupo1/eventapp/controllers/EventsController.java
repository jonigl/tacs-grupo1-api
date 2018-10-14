package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import ar.com.tacsutn.grupo1.eventapp.swagger.ApiPageable;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            @RequestParam(value = "page", required = false, defaultValue = "0")
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
    public TotalUsers getTotalUsers(@PathVariable String event_id) {
        TotalUsers totalUsers = new TotalUsers();
        totalUsers.setTotalUsers(eventService.getTotalUsersByEventId(event_id));
        return totalUsers;
    }

    @GetMapping("/events/total_events")
    @PreAuthorize("hasRole('ADMIN')")
    public TotalEvents getTotalEvents(

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @RequestParam(value = "from")
            Optional<Date> from,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @RequestParam(value = "to")
            Optional<Date> to) {

        TotalEvents totalEvents = new TotalEvents();
        long total = eventService.getTotalEventsBetween(
            from.orElse(new Date(0)),
            to.orElse(new Date(Long.MAX_VALUE))
        );

        totalEvents.setTotalEvents(total);
        return totalEvents;
    }

    /**
     * Returns a page of registered events.
     *
     * @param from only retrieve events after this date.
     * @param to only retrieve events before this date.
     *
     * @return the page of events between given period.
     */
    @GetMapping("/events/registered_events")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiPageable
    public RestPage<Event> getRegisteredTotalEvents(

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "from")
            Optional<Date> from,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "to")
            Optional<Date> to,

            @ApiIgnore
            Pageable pageable) {

        Page<EventId> eventIdPage = eventService.getEventsBetween(
            from.orElse(new Date(0)),
            to.orElse(new Date(Long.MAX_VALUE)),
            pageable
        );

        List<Event> list = eventIdPage.getContent()
            .parallelStream()
            .flatMap(event -> eventbriteClient.getEvent(event.getId())
                .map(Stream::of)
                .orElseGet(Stream::empty))
            .collect(Collectors.toList());

        Page<Event> eventPage = new PageImpl<>(list, pageable, eventIdPage.getTotalElements());

        return new RestPage<>(eventPage);
    }
}

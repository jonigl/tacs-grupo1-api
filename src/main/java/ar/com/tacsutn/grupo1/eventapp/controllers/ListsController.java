package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.SessionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "Lists", description = "list resources")
public class ListsController {

    private final EventListService eventListService;
    private final SessionService sessionService;

    @Autowired
    public ListsController(EventListService eventListService, SessionService sessionService) {
        this.eventListService = eventListService;
        this.sessionService = sessionService;
    }

    @GetMapping("/lists/{list_id}")
    @PreAuthorize("hasRole('USER')")
    public EventList get(@PathVariable Long list_id, HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        return eventListService.getById(user, list_id).orElseThrow(() ->
            new ResourceNotFoundException("List not found.")
        );
    }

    @PostMapping("/lists")
    @PreAuthorize("hasRole('USER')")
    public EventList create(
            @RequestParam(name = "name") String listName,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        return eventListService.create(user, listName);
    }

    @PutMapping("/lists/{list_id}")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse update(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %d updated",list_id));
    }

    @DeleteMapping("/lists/{list_id}")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse delete(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %d deleted",list_id));
    }

    /**
     * Returns all lists.
     */
    @GetMapping("/lists")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse getAll() {
        return new MockupResponse("All lists");
    }

    @GetMapping("/lists/{list_id}/events")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse getEvents(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Events from list %d",list_id));
    }

    @PostMapping("/lists/{list_id}/events")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse addEvent(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Event added to list %d",list_id));
    }

    @DeleteMapping("/lists/{list_id}/events/{event_id}")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse deleteEvent(@PathVariable Long list_id, @PathVariable Long event_id) {
        return new MockupResponse(String.format("Event %d deleted from list %d",event_id, list_id));
    }

}

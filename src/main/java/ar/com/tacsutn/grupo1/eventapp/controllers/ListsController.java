package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "Lists", description = "list resources")
public class ListsController {

    @GetMapping("/lists/{list_id}")
    public MockupResponse get(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %d",list_id));
    }

    @PostMapping("/lists")
    public EventList create(@RequestBody String name) {
        // TODO: El repositorio de usuarios buscar el usuario logueado, añadir la nueva lista y asignarle Id

        Long id = new Long(1);

        return new EventList(id, name);
    }

    @PutMapping("/lists/{list_id}")
    public MockupResponse update(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %d updated",list_id));
    }

    @DeleteMapping("/lists/{list_id}")
    public MockupResponse delete(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %d deleted",list_id));
    }

    /**
     * Returns all lists.
     */
    @GetMapping("/lists")
    public MockupResponse getAll() {
        return new MockupResponse("All lists");
    }

    @GetMapping("/lists/{list_id}/events")
    public MockupResponse getEvents(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Events from list %d",list_id));
    }

    @PostMapping("/lists/{list_id}/events")
    public MockupResponse addEvent(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Event added to list %d",list_id));
    }

    @DeleteMapping("/lists/{list_id}/events/{event_id}")
    public MockupResponse deleteEvent(@PathVariable Long list_id, @PathVariable Long event_id) {
        return new MockupResponse(String.format("Event %d deleted from list %d",event_id, list_id));
    }

}

package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class ListsController {

    @GetMapping("/lists/{list_id}")
    public MockupResponse get(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %s",list_id));
    }

    @PostMapping("/lists")
    public MockupResponse create() {
        return new MockupResponse(String.format("List created"));
    }

    @PutMapping("/lists/{list_id}")
    public MockupResponse update(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %s updated",list_id));
    }

    @DeleteMapping("/lists/{list_id}")
    public MockupResponse delete(@PathVariable Long list_id) {
        return new MockupResponse(String.format("List %s deleted",list_id));
    }

    @GetMapping("/lists/{list_id}/events")
    public MockupResponse getEvents(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Events from list %s",list_id));
    }

    @PostMapping("/lists/{list_id}/events")
    public MockupResponse addEvent(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Event added to list %s",list_id));
    }

    @DeleteMapping("/lists/{list_id}/events/{event_id}")
    public MockupResponse deleteEvent(@PathVariable Long list_id, @PathVariable Long event_id) {
        return new MockupResponse(String.format("Event %s deleted from list %s",event_id, list_id));
    }

}

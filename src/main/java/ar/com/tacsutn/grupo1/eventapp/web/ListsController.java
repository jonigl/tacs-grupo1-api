package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class ListsController {

    @GetMapping("/lists/{id}")
    public MockupResponse get(@PathVariable Long id) {
        return new MockupResponse(String.format("List %s",id));
    }

    @PostMapping("/lists")
    public MockupResponse create() {
        return new MockupResponse(String.format("List created"));
    }

    @PutMapping("/lists/{id}")
    public MockupResponse update(@PathVariable Long id) {
        return new MockupResponse(String.format("List %s updated",id));
    }

    @DeleteMapping("/lists/{id}")
    public MockupResponse delete(@PathVariable Long id) {
        return new MockupResponse(String.format("List %s deleted",id));
    }

}

package ar.com.tacsutn.grupo1.eventapp.web;

import ar.com.tacsutn.grupo1.eventapp.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestedController implements Controller {
    @GetMapping("/events/event")
    public MockupResponse getV1(@RequestParam(value = "id", defaultValue = "0") Long id) {

        // TODO: Add logic

        return new MockupResponse("10 usuarios estan interesados en el evento " + id.toString());
    }

    @GetMapping("/events/{event_id}/users")
    public MockupResponse getV2A(@PathVariable Long event_id) {

        // TODO: Add logic

        return new MockupResponse("Los usuarios 0, 1, y 2 estan interesados en el evento " + event_id.toString());
    }
}

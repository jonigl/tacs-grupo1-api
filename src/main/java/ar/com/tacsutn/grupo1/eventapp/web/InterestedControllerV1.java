package ar.com.tacsutn.grupo1.eventapp.web;

import ar.com.tacsutn.grupo1.eventapp.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestedControllerV1 implements Controller {
    @GetMapping("/events/event")
    public MockupResponse get(@RequestParam(value = "name", defaultValue = "Event0") String event) {

        // TODO: Add logic

        return new MockupResponse("10 users interested in '" + event + "' event.");
    }
}

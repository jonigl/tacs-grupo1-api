package ar.com.tacsutn.grupo1.eventapp.web;

import ar.com.tacsutn.grupo1.eventapp.controller.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestedControllerV1 implements Controller {
    @GetMapping("/events")
    public MockupResponse getInterested(@RequestParam(value = "event", defaultValue = "Event0") String event) {

        // TODO: Add logic

        return new MockupResponse("10 users interested in '" + event + "' event.");
    }
}

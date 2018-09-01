package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class EventController {

    private static final String template = "This is an event, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/event")
    public Greeting greeting(@RequestParam(value="name", defaultValue="default") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}

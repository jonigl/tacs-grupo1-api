package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventSumController implements Controller {
    @GetMapping("/events/total_events")
    public MockupResponse getV1(@RequestParam(value = "from", defaultValue = "01/01/1970") String fromDate,
                                @RequestParam(value = "to", defaultValue = "31/12/9999") String toDate) {

        // TODO: Add logic

        return new MockupResponse("Se registraron 100 eventos desde la fecha " + fromDate + " hasta " + toDate);
    }

    @GetMapping("/events")
    public MockupResponse getV2(@RequestParam(value = "from", defaultValue = "01/01/1970") String fromDate,
                                @RequestParam(value = "to", defaultValue = "31/12/9999") String toDate) {

        // TODO: Add logic

        return new MockupResponse("Se registraron 100 eventos desde la fecha " + fromDate + " hasta " + toDate);
    }
}

package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AlarmsController {

    @PostMapping("/alarms")
    public MockupResponse create() {
        return new MockupResponse("Alarm created");
    }

    @GetMapping("/alarms/{alarm_id}")
    public MockupResponse get(@PathVariable Long alarm_id) {
        return new MockupResponse(String.format("Alarm %d", alarm_id));
    }

    @PutMapping("/alarms/{alarm_id}")
    public MockupResponse update(@PathVariable Long alarm_id) {
        return new MockupResponse(String.format("Alarm %d updated", alarm_id));
    }

    @DeleteMapping("/alarms/{alarm_id}")
    public MockupResponse delete(@PathVariable Long alarm_id) {
        return new MockupResponse(String.format("Alarm %d deleted", alarm_id));
    }
}

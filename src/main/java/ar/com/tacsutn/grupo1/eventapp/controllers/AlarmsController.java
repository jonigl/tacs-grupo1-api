package ar.com.tacsutn.grupo1.eventapp.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Alarms", description = "alarm resources")
public class AlarmsController {

  @PostMapping("/alarms")
  public MockupResponse create() {
    return new MockupResponse("Alarm created");
  }

  @GetMapping("/alarms/{alarmId}")
  public MockupResponse get(@PathVariable Long alarmId) {
    return new MockupResponse(String.format("Alarm %d", alarmId));
  }

  @PutMapping("/alarms/{alarmId}")
  public MockupResponse update(@PathVariable Long alarmId) {
    return new MockupResponse(String.format("Alarm %d updated", alarmId));
  }

  @DeleteMapping("/alarms/{alarmId}")
  public MockupResponse delete(@PathVariable Long alarmId) {
    return new MockupResponse(String.format("Alarm %d deleted", alarmId));
  }
}

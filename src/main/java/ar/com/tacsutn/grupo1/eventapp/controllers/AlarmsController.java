package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.services.AlarmService;
import ar.com.tacsutn.grupo1.eventapp.services.SessionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Alarms", description = "alarm resources")
public class AlarmsController {

    private final AlarmService alarmService;
    private final SessionService sessionService;
    private final EventbriteClient eventbriteClient;

    @Autowired
    public AlarmsController(AlarmService alarmService, SessionService sessionService, EventbriteClient eventbriteClient) {
        this.alarmService = alarmService;
        this.sessionService = sessionService;
        this.eventbriteClient = eventbriteClient;
    }

    @PostMapping("/alarms")
    @PreAuthorize("hasRole('USER')")
    public Alarm create(@RequestBody AlarmRequest alarmRequest,
                                 HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        EventFilter eventFilter = new EventFilter()
                .setKeyword(alarmRequest.getKeyword())
                .setStartDateFrom(alarmRequest.getStartDateFrom())
                .setAddress(alarmRequest.getAddress())
                .setStartDateTo(alarmRequest.getStartDateTo())
                .setPrice(alarmRequest.getPrice());
        Alarm alarm = new Alarm(user,alarmRequest.getName(), eventFilter);
        return alarmService.save(alarm);
    }

    @GetMapping("/alarms/{alarmId}")
    @PreAuthorize("hasRole('USER')")
    public Alarm get(@PathVariable Long alarmId) {
        return alarmService.getById(alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
    }

    @PutMapping("/alarms/{alarmId}")
    @PreAuthorize("hasRole('USER')")
    public Alarm update(@PathVariable Long alarmId,
                                 @RequestBody AlarmRequest alarmRequest,
                                 HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        Alarm alarm = alarmService.getById(user,alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
        alarm.setName(alarmRequest.getName());
        alarm.getFilter()
                .setKeyword(alarmRequest.getKeyword())
                .setStartDateFrom(alarmRequest.getStartDateFrom())
                .setAddress(alarmRequest.getAddress())
                .setStartDateTo(alarmRequest.getStartDateTo())
                .setPrice(alarmRequest.getPrice());
        alarmService.save(alarm);
        return alarm;
    }

    @DeleteMapping("/alarms/{alarmId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long alarmId,
                       HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        Alarm alarm = alarmService.getById(user,alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
        alarmService.remove(alarm);
    }

    @GetMapping("/alarms/today")
    @PreAuthorize("hasRole('USER')")
    public List<AlarmResponse> getAlarmsToday(HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        List<Alarm> alarms = alarmService.getAllAlarmsByUserId(user.getId());
        return alarms.stream().map(alarm ->
                eventbriteClient.searchEvents(alarm.getFilter())
            .map(page ->
                new AlarmResponse(alarm.getId(), alarm.getName(), page.getTotalElements()))
            .orElse(new AlarmResponse(alarm.getId(), alarm.getName(), 0L)))
            .collect(Collectors.toList());
    }

    @GetMapping("/alarms/{alarmId}/fetch")
    @PreAuthorize("hasRole('USER')")
    public RestPage<Event> getEvents(
            @PathVariable Long alarmId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        Alarm alarm = alarmService.getById(user,alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
        return eventbriteClient.searchEvents(alarm.getFilter(), page).orElseThrow(() -> new ResourceNotFoundException("Events not found."));
    }

}

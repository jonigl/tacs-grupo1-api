package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.services.AlarmService;
import ar.com.tacsutn.grupo1.eventapp.services.SessionService;
import ar.com.tacsutn.grupo1.eventapp.swagger.ApiPageable;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Alarms", description = "alarm resources")
public class AlarmsController {

    private final AlarmService alarmService;
    private final SessionService sessionService;
    private final EventbriteClient eventbriteClient;

    @Autowired
    public AlarmsController(
            AlarmService alarmService,
            SessionService sessionService,
            EventbriteClient eventbriteClient) {

        this.alarmService = alarmService;
        this.sessionService = sessionService;
        this.eventbriteClient = eventbriteClient;
    }

    /**
     * Get all alarms of the authenticated user.
     *
     * @return the alarms page.
     */
    @GetMapping("/alarms")
    @PreAuthorize("hasRole('USER')")
    @ApiPageable
    public RestPage<Alarm> getAll(
            @ApiIgnore Pageable pageable,
            HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        Page<Alarm> page = alarmService.getAllAlarmsByUserId(user.getId(), pageable);
        return new RestPage<>(page);
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

        if (!eventFilter.isValid()) {
            throw new BadRequestException("Filter is not set.");
        }

        Alarm alarm = new Alarm(user, alarmRequest.getName(), eventFilter);
        return alarmService.save(alarm);
    }

    @GetMapping("/alarms/{alarmId}")
    @PreAuthorize("hasRole('USER')")
    public Alarm get(@PathVariable String alarmId) {
        return alarmService.getById(alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
    }

    @PutMapping("/alarms/{alarmId}")
    @PreAuthorize("hasRole('USER')")
    public Alarm update(@PathVariable String alarmId,
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

        if (!alarm.getFilter().isValid()) {
            throw new BadRequestException("Filter is not set.");
        }

        alarmService.save(alarm);
        return alarm;
    }

    @DeleteMapping("/alarms/{alarmId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String alarmId,
                       HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        Alarm alarm = alarmService.getById(user,alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
        alarmService.remove(alarm);
    }

    /**
     * User alarms summary
     *
     * @return a summary of alarms.
     */
    @GetMapping("/alarms/today")
    @PreAuthorize("hasRole('USER')")
    @ApiPageable
    public RestPage<AlarmResponse> getAlarmsToday(
            @ApiIgnore Pageable pageable,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        Page<Alarm> alarms = alarmService.getAllAlarmsByUserId(user.getId(), pageable);

        Stream<AlarmResponse> stream = alarms.stream().parallel().map(alarm ->
            eventbriteClient.searchEvents(alarm.getFilter())
                .map(page -> new AlarmResponse(
                    alarm.getId(), alarm.getName(), alarm.getFilter(), page.getTotalElements()
                ))
                .orElse(new AlarmResponse(
                    alarm.getId(), alarm.getName(), alarm.getFilter(), 0L
                ))
        );

        List<AlarmResponse> content = stream.collect(Collectors.toList());

        Page<AlarmResponse> page = new PageImpl<>(
            content, alarms.getPageable(), alarms.getTotalElements()
        );

        return new RestPage<>(page);
    }

    /**
     * Returns the alarm events.
     *
     * @param alarmId alarm id.
     * @return a page of events.
     */
    @GetMapping("/alarms/{alarmId}/fetch")
    @PreAuthorize("hasRole('USER')")
    public RestPage<Event> getEvents(
            @PathVariable String alarmId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        Alarm alarm = alarmService.getById(user, alarmId).orElseThrow(() -> new ResourceNotFoundException("Alarm not found."));
        return eventbriteClient.searchEvents(alarm.getFilter(), page).orElseThrow(() -> new ResourceNotFoundException("Events not found."));
    }
}

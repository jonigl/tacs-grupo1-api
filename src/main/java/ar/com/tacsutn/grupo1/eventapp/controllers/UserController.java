package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.TotalAlarms;
import ar.com.tacsutn.grupo1.eventapp.models.TotalLists;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.AlarmService;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Users", description = "user resources")
public class UserController {

    private final UserService userService;
    private final EventListService eventListService;
    private final AlarmService alarmService;

    @Autowired
    public UserController(
            UserService userService,
            EventListService eventListService,
            AlarmService alarmService) {

        this.userService = userService;
        this.eventListService = eventListService;
        this.alarmService = alarmService;
    }

    /**
     * Create a new user account.
     * @return the created user.
     */
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    /**
     * Returns a single user by identifier.
     * The administrator account is required.
     *
     * @param userId requested user's identifier.
     * @return the requested user.
     */
    @GetMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable("user_id") Long userId) {
        return userService
                .getById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User id not found"));
    }

    /**
     * Returns the total number of lists by user.
     * The administrator account is required.
     *
     * @param userId requested user's identifier.
     * @return the total number of events by user.
     */
    @GetMapping("/users/{user_id}/total_lists")
    @PreAuthorize("hasRole('ADMIN')")
    public TotalLists getTotalOfListEvents(@PathVariable("user_id") Long userId) {
        TotalLists totalLists = new TotalLists();
        totalLists.setTotalLists(eventListService.getTotalEventListByUserId(userId));
        return totalLists;
    }

    /**
     * Returns the total number of alarms by user.
     * The administrator account is required.
     *
     * @param userId requested user's identifier.
     * @return the total number of alarms by user.
     */
    @GetMapping("/users/{user_id}/total_alarms")
    @PreAuthorize("hasRole('ADMIN')")
    public TotalAlarms getTotalOfAlarms(@PathVariable("user_id") Long userId) {
        TotalAlarms totalAlarms = new TotalAlarms();
        totalAlarms.setTotalAlarms(alarmService.getTotalAlarmsByUserId(userId));
        return totalAlarms;
    }
}

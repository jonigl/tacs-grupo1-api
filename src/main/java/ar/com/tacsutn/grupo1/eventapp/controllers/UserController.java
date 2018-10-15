package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.services.AlarmService;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.SessionService;
import ar.com.tacsutn.grupo1.eventapp.services.UserService;
import ar.com.tacsutn.grupo1.eventapp.swagger.ApiPageable;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Users", description = "user resources")
public class UserController {

    private final UserService userService;
    private final EventListService eventListService;
    private final AlarmService alarmService;
    private final SessionService sessionService;

    @Autowired
    public UserController(
            UserService userService,
            EventListService eventListService,
            AlarmService alarmService,
            SessionService sessionService) {

        this.userService = userService;
        this.eventListService = eventListService;
        this.alarmService = alarmService;
        this.sessionService = sessionService;
    }

    /**
     * Returns all users.
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiPageable
    public RestPage<User> getAll(
            @ApiIgnore Pageable pageable,
            HttpServletRequest request) {

        Page<User> list = userService.getAllUsers(PageRequest.of(0, 50));
        return new RestPage<>(list);
    }


    /**
     * Returns user's lists.
     */
    @GetMapping("/users/{user_id}/lists")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiPageable
    public RestPage<EventList> getUserLists(
            @PathVariable("user_id") Long userId,
            HttpServletRequest request) {
        User user = userService.getById(userId).orElseThrow(()-> new ResourceNotFoundException("User id not found"));
        Page<EventList> list = eventListService.getListsByUserId(user, PageRequest.of(0, 50));
        return new RestPage<>(list);
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
     * Returns the currently authenticated user (by its authentication header).
     *
     * @return the authenticated user.
     */
    @GetMapping("/users/info")
    @PreAuthorize("hasRole('USER')")
    public User getUserInfo(HttpServletRequest request) {
        return sessionService.getAuthenticatedUser(request);
    }

    /**
     * Updates the authenticated user's information.
     *
     * @return the updated authenticated user.
     */
    @PatchMapping("/users/info")
    @PreAuthorize("hasRole('USER')")
    public User updateUserInfo(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        return userService.update(user, userRequest);
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

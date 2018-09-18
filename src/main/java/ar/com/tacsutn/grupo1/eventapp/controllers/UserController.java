package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.User;
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

    @Autowired
    UserService userService;

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
        return userService.getById(userId).orElseThrow(()-> new ResourceNotFoundException("User id not found"));
    }
}

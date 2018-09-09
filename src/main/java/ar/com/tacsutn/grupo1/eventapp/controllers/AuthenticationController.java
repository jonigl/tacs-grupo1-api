package ar.com.tacsutn.grupo1.eventapp.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "Authentication", description = "authentication operations")
public class AuthenticationController {

    @PostMapping("/login")
    public MockupResponse login() {
        return new MockupResponse("Logged in!");
    }

    @PostMapping("/logout")
    public MockupResponse logout() {
        return new MockupResponse("Logged in!");
    }
}

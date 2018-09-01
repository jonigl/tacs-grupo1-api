package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
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

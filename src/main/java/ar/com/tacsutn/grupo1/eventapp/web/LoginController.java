package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class LoginController {
    @PostMapping("/login")
    public Login message() {
        return new Login("Logged in!");
    }
}

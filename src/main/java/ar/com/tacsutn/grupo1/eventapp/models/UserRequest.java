package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public class UserRequest {

    private final String username;

    private final String password;

    private final String email;

    private final String firstname;

    private final String lastname;

    private final Integer telegramUserId;

    @JsonCreator
    public UserRequest(

            @JsonProperty(value = "username", required = true)
            @Size(max = 50)
            String username,

            @JsonProperty(value = "password", required = false)
            @Size(min = 4, max = 100)
            String password,

            @JsonProperty(value = "email", required = true)
            @Size(min = 4, max = 50)
            String email,

            @JsonProperty(value = "firstname", required = true)
            @Size(min = 4, max = 50)
            String firstname,

            @JsonProperty(value = "lastname", required = true)
            @Size(min = 4, max = 50)
            String lastname,

            @JsonProperty(value = "telegramUserId", required = false)
            Integer telegramUserId
    ) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.telegramUserId = telegramUserId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getTelegramUserId() {
        return telegramUserId;
    }
}

package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document
public class User {

    @Id
    private String id;

    @Field
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Field
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Field
    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @Field
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @Field
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @JsonIgnore
    @Field
    @NotNull
    private Boolean enabled;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    private LocalDateTime lastAccess;

    @JsonIgnore
    @Field
    @NotNull
    private Date lastPasswordResetDate;

    @JsonIgnore
    @DBRef
    private List<Authority> authorities;

    @JsonIgnore
    @DBRef(lazy = true)
    private List<Alarm> alarms;

    @Indexed(unique = true, sparse = true)
    private Integer telegramUserId;

    public User() {

    }

    public User(
            @Size(min = 4, max = 50) String username,
            @Size(min = 4, max = 100) String password,
            @Size(min = 4, max = 50) String firstname,
            @Size(min = 4, max = 50) String lastname,
            @Size(min = 4, max = 50) String email,
            Boolean enabled, Date lastPasswordResetDate,
            List<Authority> authorities
    ) {

        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public Integer getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(Integer telegramUserId) {
        this.telegramUserId = telegramUserId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof User) {
            return id.equals(((User) obj).getId());
        } else {
            return super.equals(obj);
        }
    }
}

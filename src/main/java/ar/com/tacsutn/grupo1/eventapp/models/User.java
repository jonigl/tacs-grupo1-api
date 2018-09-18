package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true, nullable = false)
    @Size(max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", length = 100, nullable = false)
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String email;

    @JsonIgnore
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @JsonIgnore
    @Column(name = "LASTPASSWORDRESETDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private List<Authority> authorities;

    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Alarm> alarms = Collections.emptyList();

    public User() {

    }

    public User(
            @Size(max = 50) String username,
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
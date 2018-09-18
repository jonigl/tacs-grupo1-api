package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user"}))
@ApiModel
public class Alarm {

    @Id
    @ApiModelProperty(example = "12345678")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(example = "sample alarm name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Embedded
    private EventFilter filter;

    public Alarm(){

    }

    public Alarm(User user, String name, EventFilter filter) {
        this.user = user;
        this.name = name;
        this.filter = filter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventFilter getFilter() {
        return filter;
    }

    public void setFilter(EventFilter filter) {
        this.filter = filter;
    }
}

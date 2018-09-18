package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user"}))
@ApiModel
public class Alarm {

    @Id
    @ApiModelProperty(example = "12345678")
    private Long id;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(example = "sample alarm name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Embedded
    private EventFilter filter;

    private LocalDate activationTime;

    public Alarm(User user, String name, EventFilter filter, LocalDate activationTime) {
        this.user = user;
        this.name = name;
        this.filter = filter;
        this.activationTime = activationTime;
    }

//    public List<EventId> getEvents() {
//        return events;
//    }
//
//    public int getEventsCount() {
//        return this.events.size();
//    }
}

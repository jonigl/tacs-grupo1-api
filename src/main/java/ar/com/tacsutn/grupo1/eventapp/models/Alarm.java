package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.repositories.EventsRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Entity
@ApiModel
public class Alarm extends TimerTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(example = "12345678")
    private long id;

    @Column(nullable = false)
    @ApiModelProperty(example = "sample alarm name")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(example = "sample criteria to find")
    private Criteria criteria;

    private List<Event> events;

    private Date activationTime;

    private Timer timer;

    private EventsRepository repository;

    public Alarm(long id, String name, Criteria criteria, Date activationTime, EventsRepository repository) {
        this.id = id;
        this.name = name;
        this.criteria = criteria;
        this.events = new ArrayList<Event>();
        this.activationTime = activationTime;
        this.timer = new Timer();
        this.repository = repository;

        timer.scheduleAtFixedRate(this, activationTime, TimeUnit.DAYS.toMillis(1));
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public void run() {
        this.events = this.repository.search(this.criteria);
    }

    public int getEventsCount() {
        return this.events.size();
    }
}

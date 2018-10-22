package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document
@ApiModel
public class Alarm {

    @Id
    @ApiModelProperty(example = "12345678")
    private String id;

    @Field("name")
    @Indexed(unique = true)
    @NotNull
    @ApiModelProperty(example = "sample alarm name")
    private String name;

    @JsonIgnore
    @DBRef
    @NotNull
    private User user;

    private EventFilter filter;

    public Alarm() {

    }

    public Alarm(User user, String name, EventFilter filter) {
        this.user = user;
        this.name = name;
        this.filter = filter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

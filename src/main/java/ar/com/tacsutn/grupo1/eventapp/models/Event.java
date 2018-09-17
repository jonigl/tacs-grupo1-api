package ar.com.tacsutn.grupo1.eventapp.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

@ApiModel
public class Event {

    @ApiModelProperty(example = "12345abc")
    private String id;

    @ApiModelProperty(example = "sample event")
    private String name;

    @ApiModelProperty(example = "sample event description")
    private String description;

    private LocalDateTime createdDate;

    // TODO: Add more fields.

    public Event(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = LocalDateTime.now();
    }

    public Event(String id, String name, String description , LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInDateRange(LocalDateTime from, LocalDateTime to) {
        return this.createdDate.isAfter(from) && this.createdDate.isBefore(to);
    }
}

package ar.com.tacsutn.grupo1.eventapp.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Event {

    @ApiModelProperty(example = "12345abc")
    private String id;

    @ApiModelProperty(example = "sample event")
    private String name;

    @ApiModelProperty(example = "sample event description")
    private String description;

    // TODO: Add more fields.

    public Event(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}

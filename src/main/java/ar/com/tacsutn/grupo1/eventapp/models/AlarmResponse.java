package ar.com.tacsutn.grupo1.eventapp.models;

public class AlarmResponse {

    private Long id;

    private String name;

    private Long total_events_matched;

    public AlarmResponse(Long id, String name, Long total_events_matched) {
        this.id = id;
        this.name = name;
        this.total_events_matched = total_events_matched;
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

    public Long getTotal_events_matched() {
        return total_events_matched;
    }

    public void setTotal_events_matched(Long total_events_matched) {
        this.total_events_matched = total_events_matched;
    }
}

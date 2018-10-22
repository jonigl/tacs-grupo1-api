package ar.com.tacsutn.grupo1.eventapp.models;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;

public class AlarmResponse {

    private String id;

    private String name;

    private Long total_events_matched;

    private EventFilter filter;

    public AlarmResponse(String id, String name, EventFilter filter, Long total_events_matched) {
        this.id = id;
        this.name = name;
        this.filter = filter;
        this.total_events_matched = total_events_matched;
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

    public Long getTotal_events_matched() {
        return total_events_matched;
    }

    public void setTotal_events_matched(Long total_events_matched) {
        this.total_events_matched = total_events_matched;
    }

    public EventFilter getFilter() {
        return filter;
    }

    public void setFilter(EventFilter filter) {
        this.filter = filter;
    }
}

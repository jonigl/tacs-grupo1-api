package ar.com.tacsutn.grupo1.eventapp.client;

import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class EventSearchPage {

    private final Pagination pagination;
    private final List<EventTemplate> events;

    @JsonCreator
    public EventSearchPage(

            @JsonProperty(value = "pagination", required = true)
            Pagination pagination,

            @JsonProperty(value = "events", required = true)
            List<EventTemplate> events
    ) {

        this.pagination = pagination;
        this.events = events;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<EventTemplate> getEvents() {
        return events;
    }

    public RestPage<EventTemplate> toRestPage() {
        Pageable pageable = PageRequest.of(
            pagination.getPageNumber() - 1,
            pagination.getPageSize()
        );

        List<EventTemplate> content = events == null ? Collections.emptyList() : events;

        Page<EventTemplate> page = new PageImpl<>(
            content,
            pageable,
            pagination.getObjectCount()
        );

        return new RestPage<>(page);
    }
}

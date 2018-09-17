package ar.com.tacsutn.grupo1.eventapp.client;

import ar.com.tacsutn.grupo1.eventapp.models.Event;
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
    private final List<Event> events;

    @JsonCreator
    public EventSearchPage(

            @JsonProperty(value = "pagination", required = true)
            Pagination pagination,

            @JsonProperty(value = "events", required = true)
            List<Event> events
    ) {

        this.pagination = pagination;
        this.events = events;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Event> getEvents() {
        return events;
    }

    public RestPage<Event> toRestPage() {
        Pageable pageable = PageRequest.of(
            pagination.getPageNumber() - 1,
            pagination.getPageSize()
        );

        List<Event> content = events == null ? Collections.emptyList() : events;

        Page<Event> page = new PageImpl<>(
            content,
            pageable,
            pagination.getObjectCount()
        );

        return new RestPage<>(page);
    }
}

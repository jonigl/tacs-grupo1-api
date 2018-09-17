package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Lists", description = "list resources")
public class ListCompareController {

    /**
     * Returns all the common events from two event lists.
     * @param listId1 first event list identifier.
     * @param listId2 second event list identifier.
     */
    @GetMapping("/lists/compare")
    @PreAuthorize("hasRole('ADMIN')")
    public RestPage<Event> getCommonEvents(@RequestParam(name = "list1") Long listId1,
                                           @RequestParam(name = "list2") Long listId2) {
        Event event1 = new Event(
            "1", "sample event 1", "event 1 description",
            null, null, null,
            null, null, null,
            null, false
        );

        Event event2 = new Event(
            "2", "sample event 2", "event 2 description",
            null, null, null,
            null, null, null,
            null, false
        );

        Page<Event> page = new PageImpl<>(
            Arrays.asList(event1, event2),
            PageRequest.of(0, 10),
            2
        );

        return new RestPage<>(page);
    }
}

package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Lists", description = "list resources")
public class ListCompareController {

    private final EventListService listService;

    @Autowired
    public ListCompareController(EventListService listService) {
        this.listService = listService;
    }

    /**
     * Returns all the common events from two event lists.
     * @param listId1 first event list identifier.
     * @param listId2 second event list identifier.
     */
    @GetMapping("/lists/compare")
    @PreAuthorize("hasRole('ADMIN')")
    public RestPage<EventId> getCommonEvents(
            @RequestParam(name = "list1") Long listId1,
            @RequestParam(name = "list2") Long listId2) {

        return new RestPage<>(listService.getCommonEvents(listId1, listId2));
    }
}

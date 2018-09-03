package ar.com.tacsutn.grupo1.eventapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ListCompareController {

    /**
     * Returns all the common events from two event lists.
     * @param listId1 first event list identifier.
     * @param listId2 second event list identifier.
     */
    @GetMapping("/lists/compare")
    public MockupResponse getCommonEvents(@RequestParam(name = "list1") Long listId1,
                                          @RequestParam(name = "list2") Long listId2) {
        String message = String.format("Common events from list %d and list %d.", listId1, listId2);
        return new MockupResponse(message);
    }
}

package ar.com.tacsutn.grupo1.eventapp.client;

import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
public class EventbriteClient {

    private final String BASE_URL = "https://www.eventbriteapi.com/v3";

    @Value("${eventbrite.oauth-token}")
    private String TOKEN;

    private final RestTemplate restTemplate;

    public EventbriteClient() {
        restTemplate = new RestTemplate();
    }

    public Optional<Event> getEvent(String id) {
        UriComponents uriComponents = getUriComponentsBuilder()
                .path("/events/{event_id}")
                .buildAndExpand(id)
                .encode();

        return request(uriComponents.toUri(), Event.class);
    }

    public Optional<RestPage<Event>> searchEvents(EventFilter filter) {
        return this.searchEvents(filter, 0);
    }

    public Optional<RestPage<Event>> searchEvents(EventFilter filter, Integer pageNumber) {
        UriComponentsBuilder uriComponentsBuilder = getUriComponentsBuilder();

        if (pageNumber > 0) {
            uriComponentsBuilder.queryParam("page", pageNumber + 1);
        }

        UriComponents uriComponents = uriComponentsBuilder
                .path("/events/search")
                .queryParams(filter.getQueryParams())
                .build()
                .encode();

        Optional<EventSearchPage> page = request(uriComponents.toUri(), EventSearchPage.class);
        return page.map(EventSearchPage::toRestPage);
    }

    private <T> Optional<T> request(URI uri, Class<T> responseType) {
        ResponseEntity<T> response;

        try {
             response = restTemplate.getForEntity(uri, responseType);
        } catch (RestClientException e) {
            return Optional.empty();
        }

        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return Optional.of(response.getBody());
        }

        return Optional.empty();
    }

    private UriComponentsBuilder getUriComponentsBuilder() {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("token", TOKEN);
    }

    RestTemplate getRestTemplate() {
        return restTemplate;
    }
}

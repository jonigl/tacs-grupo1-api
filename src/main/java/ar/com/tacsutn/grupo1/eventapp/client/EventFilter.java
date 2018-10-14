package ar.com.tacsutn.grupo1.eventapp.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventFilter {

    private static final String KEYWORD_KEY = "q";
    private static final String START_DATE_FROM_KEY = "start_date.range_start";
    private static final String START_DATE_TO_KEY = "start_date.range_end";
    private static final String ADDRESS_KEY = "location.address";
    private static final String PRICE_KEY = "price";

    private String keyword;

    private LocalDateTime startDateFrom;

    private LocalDateTime startDateTo;

    private String address;

    private String price;

    public String getKeyword() {
        return keyword;
    }

    public String getStartDateFrom() {
        return startDateFrom == null ? null : formatDateTime(startDateFrom);
    }

    public String getStartDateTo() {
        return startDateTo == null ? null : formatDateTime(startDateTo);
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public EventFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public EventFilter setStartDateFrom(LocalDateTime startDateFrom) {
        this.startDateFrom = startDateFrom;
        return this;
    }

    public EventFilter setStartDateTo(LocalDateTime startDateTo) {
        this.startDateTo = startDateTo;
        return this;
    }

    public EventFilter setAddress(String address) {
        this.address = address;
        return this;
    }

    public EventFilter setPrice(String price) {
        this.price = price;
        return this;
    }

    MultiValueMap<String, String> getQueryParams() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.set(KEYWORD_KEY, getKeyword());
        multiValueMap.set(START_DATE_FROM_KEY, getStartDateFrom());
        multiValueMap.set(START_DATE_TO_KEY, getStartDateTo());
        multiValueMap.set(ADDRESS_KEY, getAddress());
        multiValueMap.set(PRICE_KEY, getPrice());

        multiValueMap.values().removeIf(elem -> elem.stream().allMatch(Objects::isNull));
        return multiValueMap;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ISO_DATE_TIME.format(dateTime.withNano(0));
    }
}

package ar.com.tacsutn.grupo1.eventapp.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventFilter {

    private String keyword;

    private LocalDateTime startDateFrom;

    private LocalDateTime startDateTo;

    private String address;

    private String price;

    @JsonProperty("q")
    public String getKeyword() {
        return keyword;
    }

    @JsonProperty("start_date.range_start")
    public String getStartDateFrom() {
        return startDateFrom == null ? null : formatDateTime(startDateFrom);
    }

    @JsonProperty("start_date.range_end")
    public String getStartDateTo() {
        return startDateTo == null ? null : formatDateTime(startDateTo);
    }

    @JsonProperty("location.address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("price")
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
        ObjectMapper mapper = new ObjectMapper();
        TypeReference typeReference = new TypeReference<HashMap<String, String>>() {};
        HashMap<String, String> map = mapper.convertValue(this, typeReference);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(map);

        return multiValueMap;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ISO_DATE_TIME.format(dateTime);
    }
}

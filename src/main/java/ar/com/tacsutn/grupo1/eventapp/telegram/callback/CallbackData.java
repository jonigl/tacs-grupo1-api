package ar.com.tacsutn.grupo1.eventapp.telegram.callback;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallbackData {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public enum Type {
        SELECTED_EVENT, SELECTED_LIST, SHOW_LIST_EVENTS, SHOW_EVENT_DETAILS
    }

    @JsonProperty("t")
    private Type type;

    @JsonProperty("e")
    private String eventId;

    @JsonProperty("l")
    private String listId;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public static String serialize(CallbackData callbackData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(callbackData);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public static Optional<CallbackData> deserialize(String s) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            CallbackData callbackData = objectMapper.readValue(s, CallbackData.class);
            return Optional.ofNullable(callbackData);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

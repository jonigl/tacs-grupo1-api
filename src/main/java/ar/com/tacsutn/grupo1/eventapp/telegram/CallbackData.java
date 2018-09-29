package ar.com.tacsutn.grupo1.eventapp.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class CallbackData {

    @JsonProperty("c")
    private Long chatId;

    @JsonProperty("e")
    private String eventId;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.CallbackData;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ShowListEventsCallbackOperation extends AuthenticatedCallbackOperation {

    private final EventbriteClient eventbriteClient;
    private final EventService eventService;

    @Autowired
    protected ShowListEventsCallbackOperation(
            TelegramUserRepository telegramUserRepository,
            EventbriteClient eventbriteClient,
            EventService eventService) {

        super(telegramUserRepository);
        this.eventbriteClient = eventbriteClient;
        this.eventService = eventService;
    }

    @Override
    public void handle(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        getUserOrAlert(bot, callbackQuery)
                .map(user -> eventService.getIdsByEventListId(callbackData.getListId(), PageRequest.of(0, 5)))
                .map(eventIds -> getShowListEventsMessageRequest(callbackQuery, eventIds))
                .ifPresent(request -> makeRequest(bot, request));
    }

    private SendMessage getShowListEventsMessageRequest(CallbackQuery callbackQuery, Page<EventId> eventIds) {
        return new SendMessage()
                .setChatId((long) callbackQuery.getFrom().getId())
                .setText("Seleccione un evento para ver más detalles:")
                .setReplyMarkup(getKeyboardMarkupFromEventIds(eventIds));
    }

    private InlineKeyboardMarkup getKeyboardMarkupFromEventIds(Page<EventId> eventIds) {
        return new InlineKeyboardMarkup()
                .setKeyboard(getKeyboardFromEventIds(eventIds));
    }

    private List<List<InlineKeyboardButton>> getKeyboardFromEventIds(Page<EventId> eventIds) {
        return eventIds.stream().parallel()
                .flatMap(eventId -> eventbriteClient.getEvent(eventId.getId())
                    .map(this::getKeyboardButtonFromEvent)
                    .map(Collections::singletonList)
                    .map(Stream::of)
                    .orElseGet(Stream::empty))
                .collect(Collectors.toList());
    }

    private InlineKeyboardButton getKeyboardButtonFromEvent(Event event) {
        CallbackData callbackData = new CallbackData();
        callbackData.setType(CallbackData.Type.SHOW_EVENT_DETAILS);
        callbackData.setEventId(event.getId());

        return new InlineKeyboardButton()
                .setText(event.getName())
                .setCallbackData(CallbackData.serialize(callbackData));
    }
}

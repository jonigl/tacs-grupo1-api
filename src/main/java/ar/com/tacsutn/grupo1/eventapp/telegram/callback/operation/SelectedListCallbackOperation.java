package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.CallbackData;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Component
public class SelectedListCallbackOperation extends AuthenticatedCallbackOperation {

    private final EventService eventService;
    private final EventListService eventListService;

    @Autowired
    protected SelectedListCallbackOperation(
            TelegramUserRepository telegramUserRepository,
            EventService eventService,
            EventListService eventListService) {

        super(telegramUserRepository);
        this.eventService = eventService;
        this.eventListService = eventListService;
    }

    /**
     * Process a callback query of a selected list (and event) by the user.
     * @param bot the telegram bot.
     * @param callbackQuery the callback query with the selected list and event data.
     */
    @Override
    public void handle(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        EventId eventId = new EventId(callbackData.getEventId());
        Long listId = callbackData.getListId();

        getUserOrAlert(bot, callbackQuery)
                .flatMap(user -> addEventToList(user.getInternalUser(), listId, eventId))
                .map(list -> answerAddedEventToList(callbackQuery, list))
                .ifPresent(answer -> makeRequest(bot, answer));
    }

    /**
     * Creates the response message request when the event is added successfully to the list.
     * @param callbackQuery the original callback query requested the add operation.
     * @param eventList the event list with the recently added event.
     * @return the response message request.
     */
    private SendMessage answerAddedEventToList(CallbackQuery callbackQuery, EventList eventList) {
        return new SendMessage()
                .setChatId((long) callbackQuery.getFrom().getId())
                .setText("El evento fue añadido a la lista \"" + eventList.getName() + "\".");
    }

    @Transactional
    private Optional<EventList> addEventToList(User user, Long listId, EventId eventId) {
        try {
            eventService.save(eventId);
            return eventListService.addEvent(user, listId, eventId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.CallbackData;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUser;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SelectedEventCallbackOperation extends AuthenticatedCallbackOperation {

    private final EventListService eventListService;

    @Autowired
    protected SelectedEventCallbackOperation(
            TelegramUserRepository telegramUserRepository,
            EventListService eventListService) {

        super(telegramUserRepository);
        this.eventListService = eventListService;
    }

    /**
     * Process a callback query of a selected event by the user.
     * @param bot the telegram bot.
     * @param callbackQuery the callback query with the selected event data.
     */
    @Override
    public void handle(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        String eventId = callbackData.getEventId();

        getUserOrAlert(bot, callbackQuery)
            .map(user -> answerSelectedEvent(user, eventId))
            .ifPresent(answer -> makeRequest(bot, answer));
    }

    /**
     * Creates the send message request next to the event selection by the user.
     * @param user the Telegram user.
     * @param eventId the selected event's identifier.
     * @return the send message request.
     */
    private SendMessage answerSelectedEvent(TelegramUser user, String eventId) {
        User internalUser = user.getInternalUser();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup()
            .setKeyboard(getKeyboard(internalUser, eventId));

        return new SendMessage()
            .setChatId((long) user.getTelegramUserId())
            .setText("Seleccione una lista para añadir:")
            .setReplyMarkup(inlineKeyboardMarkup);
    }

    /**
     * Creates the inline keyboard with user's event lists as options.
     * @param user the user.
     * @param eventId the identifier of the selected event to be added to a list.
     * @return the created keyboard.
     */
    private List<List<InlineKeyboardButton>> getKeyboard(User user, String eventId) {
        // TODO: Pagination.
        Page<EventList> eventLists = eventListService.getListsByUser(user);
        return eventLists.stream()
            .map(list -> getKeyboardRow(list, eventId))
            .collect(Collectors.toList());
    }

    private List<InlineKeyboardButton> getKeyboardRow(EventList eventList, String eventId) {
        CallbackData callbackData = new CallbackData();
        callbackData.setType(CallbackData.Type.SELECTED_LIST);
        callbackData.setEventId(eventId);
        callbackData.setListId(eventList.getId());

        InlineKeyboardButton button = new InlineKeyboardButton()
            .setText(eventList.getName())
            .setCallbackData(CallbackData.serialize(callbackData));

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        return row;
    }
}

package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUser;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CallbackQueryHandler {

    private final TelegramUserRepository telegramUserRepository;
    private final EventListService eventListService;

    @Autowired
    public CallbackQueryHandler(
            TelegramUserRepository telegramUserRepository,
            EventListService eventListService) {

        this.telegramUserRepository = telegramUserRepository;
        this.eventListService = eventListService;
    }

    /**
     * Processes a callback query triggered by an inline button.
     * @param bot the telegram bot.
     * @param update the update event containing the callback query.
     */
    public void handle(TelegramBot bot, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        CallbackData.deserialize(callbackQuery.getData()).ifPresent(callbackData -> {
            switch (callbackData.getType()) {
                case SELECTED_EVENT:
                    handleSelectedEvent(bot, callbackQuery, callbackData);
                case SELECTED_LIST:
                    handleSelectedList(bot, callbackQuery, callbackData);
            }
        });
    }

    /**
     * Process a callback query of a selected event by the user.
     * @param bot the telegram bot.
     * @param callbackQuery the callback query with the selected event data.
     */
    private void handleSelectedEvent(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        Integer userId = callbackQuery.getFrom().getId();
        Optional<TelegramUser> telegramUser = telegramUserRepository.getByTelegramUserId(userId);

        String eventId = callbackData.getEventId();

        Optional<BotApiMethod<? extends Serializable>> answer = telegramUser.map(u ->
            answerAuthenticated(u, eventId)
        );

        BotApiMethod<? extends Serializable> request = answer.orElseGet(() ->
            answerUnauthenticated(callbackQuery)
        );

        try {
            bot.executeAsync(request, new BaseSentCallback<>());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Process a callback query of a selected list (and event) by the user.
     * @param bot the telegram bot.
     * @param callbackQuery the callback query with the selected list and event data.
     */
    private void handleSelectedList(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        // TODO
    }

    private SendMessage answerAuthenticated(TelegramUser user, String eventId) {
        User internalUser = user.getInternalUser();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup()
                .setKeyboard(getKeyboard(internalUser, eventId));

        return new SendMessage()
                .setChatId((long) user.getTelegramUserId())
                .setText("Seleccione una lista para añadir:")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    private AnswerCallbackQuery answerUnauthenticated(CallbackQuery callbackQuery) {
        return new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setText("Debe loguearse primero.")
                .setShowAlert(true);
    }

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

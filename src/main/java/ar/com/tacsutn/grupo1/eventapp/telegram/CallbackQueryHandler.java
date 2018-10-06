package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUser;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    private final EventService eventService;
    private final EventListService eventListService;

    @Autowired
    public CallbackQueryHandler(
            TelegramUserRepository telegramUserRepository,
            EventService eventService,
            EventListService eventListService) {

        this.telegramUserRepository = telegramUserRepository;
        this.eventService = eventService;
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
        String eventId = callbackData.getEventId();

        getUserOrAlert(bot, callbackQuery)
                .map(user -> answerSelectedEvent(user, eventId))
                .ifPresent(answer -> makeRequest(bot, answer));
    }

    /**
     * Process a callback query of a selected list (and event) by the user.
     * @param bot the telegram bot.
     * @param callbackQuery the callback query with the selected list and event data.
     */
    private void handleSelectedList(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        EventId eventId = new EventId(callbackData.getEventId());
        Long listId = callbackData.getListId();

        getUserOrAlert(bot, callbackQuery)
                .flatMap(user -> addEventToList(user.getInternalUser(), listId, eventId))
                .map(list -> answerAddedEventToList(callbackQuery, list))
                .ifPresent(answer -> makeRequest(bot, answer));
    }

    /**
     * Makes an async request to Telegram.
     * @param bot the Telegram bot.
     * @param request the request.
     */
    private void makeRequest(TelegramBot bot, BotApiMethod<? extends Serializable> request) {
        try {
            bot.executeAsync(request, new BaseSentCallback<>());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the specified Telegram user or sends a login request alert if unauthenticated.
     * @param bot the Telegram bot.
     * @param callbackQuery the callback query required authentication.
     * @return the Telegram user.
     */
    private Optional<TelegramUser> getUserOrAlert(TelegramBot bot, CallbackQuery callbackQuery) {
        Integer userId = callbackQuery.getFrom().getId();
        Optional<TelegramUser> telegramUser = telegramUserRepository.getByTelegramUserId(userId);
        if (!telegramUser.isPresent()) {
            AnswerCallbackQuery answer = answerUnauthenticated(callbackQuery);
            makeRequest(bot, answer);
        }

        return telegramUser;
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

    /**
     * Creates the login request alert to an unauthenticated operation.
     * @param callbackQuery the callback query with unauthenticated operation.
     * @return the answer request.
     */
    private AnswerCallbackQuery answerUnauthenticated(CallbackQuery callbackQuery) {
        return new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setText("Debe loguearse primero.")
                .setShowAlert(true);
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

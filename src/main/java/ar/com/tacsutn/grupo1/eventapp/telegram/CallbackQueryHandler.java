package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUser;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
     * Processes an callback query triggered by an inline button.
     * @param bot the telegram bot.
     * @param update the update event containing the callback query.
     */
    public void handle(TelegramBot bot, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery.getData().startsWith("add ")) {
            handleAdd(bot, callbackQuery);
        }
    }

    private void handleAdd(TelegramBot bot, CallbackQuery callbackQuery) {
        // TODO: Get user by telegram user id, then get chat id by user.

        Integer userId = callbackQuery.getFrom().getId();
        Optional<TelegramUser> telegramUser = telegramUserRepository.getByTelegramUser(userId);

        Optional<SendMessage> request = telegramUser.map(user -> {
            Long chatId = user.getTelegramChat();
            User internalUser = user.getInternalUser();

//            String eventId = getEventId(callbackQuery);
            String text = "Seleccione una lista para añadir el evento.";

            ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup().setKeyboard(getKeyboard(internalUser));
            return new SendMessage(chatId, text).setReplyMarkup(replyMarkup);
        });

//        try {
//            bot.executeAsync(request, new BaseSentCallback<>());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

    private List<KeyboardRow> getKeyboard(User user) {
        Page<EventList> eventLists = eventListService.getListsByUser(user);
        return eventLists.stream()
                .map(this::getKeyboardRow)
                .collect(Collectors.toList());
    }

    private KeyboardRow getKeyboardRow(EventList eventList) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(eventList.getName());
        return keyboardRow;
    }

    private String getEventId(CallbackQuery callbackQuery) {
        return callbackQuery.getData().replaceFirst("^add ", "");
    }
}

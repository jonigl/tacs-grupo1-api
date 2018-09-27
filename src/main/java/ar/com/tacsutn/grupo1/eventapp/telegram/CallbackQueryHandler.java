package ar.com.tacsutn.grupo1.eventapp.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackQueryHandler {

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
        // TODO: Get user by chat id.
    }
}

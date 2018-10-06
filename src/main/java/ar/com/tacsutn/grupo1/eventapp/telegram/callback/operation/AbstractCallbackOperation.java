package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.telegram.BaseSentCallback;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

abstract class AbstractCallbackOperation implements CallbackOperation {

    /**
     * Makes an async request to Telegram.
     * @param bot the Telegram bot.
     * @param request the request.
     */
    protected void makeRequest(TelegramBot bot, BotApiMethod<? extends Serializable> request) {
        try {
            bot.executeAsync(request, new BaseSentCallback<>());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

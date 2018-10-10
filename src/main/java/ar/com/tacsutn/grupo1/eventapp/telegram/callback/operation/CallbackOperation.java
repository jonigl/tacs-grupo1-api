package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.CallbackData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackOperation {

    void handle(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData);
}

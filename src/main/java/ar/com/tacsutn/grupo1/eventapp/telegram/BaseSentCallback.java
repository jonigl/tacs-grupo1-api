package ar.com.tacsutn.grupo1.eventapp.telegram;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;

public class BaseSentCallback<T extends Serializable> implements SentCallback<T> {

    @Override
    public void onResult(BotApiMethod<T> botApiMethod, T t) {

    }

    @Override
    public void onError(BotApiMethod<T> botApiMethod, TelegramApiRequestException e) {
        e.printStackTrace();
    }

    @Override
    public void onException(BotApiMethod<T> botApiMethod, Exception e) {
        e.printStackTrace();
    }
}

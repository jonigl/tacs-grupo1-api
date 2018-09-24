package ar.com.tacsutn.grupo1.eventapp.telegram;

import com.pengrad.telegrambot.model.Update;

@FunctionalInterface
public interface Callback {

    void call(Update update);
}

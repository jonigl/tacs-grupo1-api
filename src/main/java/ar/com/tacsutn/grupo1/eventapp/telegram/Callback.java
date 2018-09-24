package ar.com.tacsutn.grupo1.eventapp.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;

@FunctionalInterface
public interface Callback {

    void call(Update update);
}

package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface BotCommand {

    void run(TelegramBot bot, Update event) throws TelegramApiException;
}

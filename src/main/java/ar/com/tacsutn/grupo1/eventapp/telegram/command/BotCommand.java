package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBotService;
import com.pengrad.telegrambot.model.Update;

public interface BotCommand {

    void run(TelegramBotService botService, Update event);
}

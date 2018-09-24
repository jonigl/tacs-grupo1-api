package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SearchCommand implements BotCommand {

    private final EventbriteClient eventbriteClient;

    @Autowired
    public SearchCommand(EventbriteClient eventbriteClient) {
        this.eventbriteClient = eventbriteClient;
    }

    @Override
    public void run(TelegramBot bot, Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage(chatId, "actions");
        bot.execute(sendMessage);
    }
}

package ar.com.tacsutn.grupo1.eventapp.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class TelegramBotService {

    private final TelegramBot bot;

    TelegramBotService(@Value("${telegram.bot-token}") String token) {
        bot = new TelegramBot.Builder(token).build();
    }

    @PostConstruct
    public void execute() {
        GetUpdates getUpdates = new GetUpdates().allowedUpdates("message");

        bot.setUpdatesListener(updates -> {
            updates.forEach(this::onUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, getUpdates);
    }

    private void onUpdate(Update update) {
        String message = update.message().text();
        if (message != null) {
            /* Echo */
            SendMessage request = new SendMessage(update.message().chat().id(), message);
            bot.execute(request);
        }
    }

    @PreDestroy
    public void stop() {
        bot.removeGetUpdatesListener();
    }
}

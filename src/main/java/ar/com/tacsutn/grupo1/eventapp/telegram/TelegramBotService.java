package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.telegram.command.BotCommandFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Service
public class TelegramBotService {

    private final TelegramBot bot;
    private final BotCommandFactory botCommandFactory;
    private Map<String, Callback> callbackQueryCallbacks = new Hashtable<>();

    @Autowired
    TelegramBotService(
            BotCommandFactory botCommandFactory,
            @Value("${telegram.bot-token}") String token) {

        bot = new TelegramBot.Builder(token).build();
        this.botCommandFactory = botCommandFactory;
    }

    @PostConstruct
    public void execute() {
        GetUpdates getUpdates = new GetUpdates().allowedUpdates("message", "callback_query");

        bot.setUpdatesListener(updates -> {
            updates.forEach(this::onUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, getUpdates);
    }

    private void onUpdate(Update update) {
        if (update.callbackQuery() != null) {
            onCallbackQuery(update);
        } else {
            onMessage(update);
        }
    }

    private void onMessage(Update update) {
        List<MessageEntity> entities = Optional.ofNullable(update.message().entities())
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList);

        entities.stream()
                .filter(entity -> entity.offset() == 0
                        && entity.type() == MessageEntity.Type.bot_command)
                .map(entity -> update.message().text().substring(
                        entity.offset(),
                        entity.offset() + entity.length()
                ))
                .forEach(command -> runCommand(command, update));
    }

    private void onCallbackQuery(Update update) {
        Callback callback = callbackQueryCallbacks.remove(update.callbackQuery().data());
        callback.call(update);
    }

    public void setCallbackQueryListener(String callbackData, Callback callback) {
        callbackQueryCallbacks.put(callbackData, callback);
    }

    private void runCommand(String commandName, Update update) {
        botCommandFactory.getCommand(commandName)
                         .ifPresent(command -> command.run(this, update));
    }

    public TelegramBot getBot() {
        return bot;
    }

    @PreDestroy
    public void stop() {
        bot.removeGetUpdatesListener();
    }
}

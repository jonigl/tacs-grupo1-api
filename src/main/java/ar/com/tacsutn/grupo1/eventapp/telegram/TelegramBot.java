package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.telegram.command.BotCommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot-token}")
    private String botToken;

    private final BotCommandFactory botCommandFactory;
    private final InlineQueryHandler inlineQueryHandler;

    private Map<String, Callback> callbackQueryCallbacks = new Hashtable<>();

    @Autowired
    public TelegramBot(
            BotCommandFactory botCommandFactory,
            InlineQueryHandler inlineQueryHandler) {

        this.botCommandFactory = botCommandFactory;
        this.inlineQueryHandler = inlineQueryHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            onCallbackQuery(update);
        } else if (update.hasInlineQuery()) {
            onInlineQuery(update);
        } else if (update.hasMessage() && update.getMessage().isCommand()){
            onCommand(update);
        }
    }

    private void onInlineQuery(Update update) {
        inlineQueryHandler.handle(this, update);
    }

    private void onCommand(Update update) {
        List<MessageEntity> entities = update.getMessage().getEntities();

        entities.stream()
                .filter(entity -> entity.getType().equals("bot_command")
                            && entity.getOffset() == 0)
                .forEach(command -> runCommand(command.getText(), update));
    }

    /**
     * Runs a bot command by the command name.
     * @param commandName the command name.
     * @param update the update event of the command message.
     */
    private void runCommand(String commandName, Update update) {
        botCommandFactory.getCommand(commandName)
                .ifPresent(command -> {
                    try {
                        command.run(this, update);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void onCallbackQuery(Update update) {
        Callback callback = callbackQueryCallbacks.remove(update.getCallbackQuery().getData());
        if (callback != null) {
            callback.call(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "EventAppBot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

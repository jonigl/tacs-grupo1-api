package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchCommand implements BotCommand {

    private final EventbriteClient eventbriteClient;

    @Autowired
    public SearchCommand(EventbriteClient eventbriteClient) {
        this.eventbriteClient = eventbriteClient;
    }

    @Override
    public void run(TelegramBotService botService, Update update) {
        Long chatId = update.message().chat().id();

        String callbackData = "test" + update.message().messageId();

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("test").callbackData(callbackData),
                new InlineKeyboardButton("test2").switchInlineQueryCurrentChat("search")
            }
        );

        SendMessage sendMessage = new SendMessage(chatId, "actions")
                .replyMarkup(inlineKeyboard);

        botService.getBot().execute(sendMessage);

        botService.setCallbackQueryListener(callbackData, callbackUpdate ->
            botService.getBot().execute(new SendMessage(chatId, callbackData))
        );
    }

    private String getKeyword(Update update) {
        return update.message().text().substring(0, "/search".length());
    }
}

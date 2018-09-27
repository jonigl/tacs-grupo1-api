package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchCommand implements BotCommand {

    private final InlineKeyboardMarkup replyMarkup;

    @Autowired
    public SearchCommand() {
        replyMarkup = createReplyMarkup();
    }

    @Override
    public void run(TelegramBot bot, Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String text =
            "Para iniciar una búsqueda, simplemente escribir ***'@" +
            bot.getBotUsername() +
            " ...'*** o hacer clic sobre el boton ***'Iniciar búsqueda'***.";
        SendMessage sendMessage = new SendMessage(chatId, text)
            .setParseMode("markdown")
            .setReplyMarkup(replyMarkup);
        bot.execute(sendMessage);
    }

    /**
     * Creates the message markup with the search button.
     * @return the reply markup
     */
    private InlineKeyboardMarkup createReplyMarkup() {
        String emoji = "\uD83D\uDD0D";
        InlineKeyboardButton searchButton = new InlineKeyboardButton(emoji + " Iniciar búsqueda");
        searchButton.setSwitchInlineQueryCurrentChat("");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(searchButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        return markup;
    }
}

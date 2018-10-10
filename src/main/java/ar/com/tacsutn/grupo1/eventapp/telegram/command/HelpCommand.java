package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.telegram.BaseSentCallback;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HelpCommand implements BotCommand {

    private static final String HELP_MESSAGE =
            "Este bot puede ayudarte a buscar eventos en Eventbrite, " +
            "revisar tus listas de eventos y agregar nuevos eventos " +
            "a tus listas.\n\n" +
            "Lista de comandos:\n" +
            "/search    Buscar en Eventbrite.\n" +
            "/mylists   Ver tus listas de eventos.\n" +
            "/help      Mostrar ayuda.";

    @Override
    public void run(TelegramBot bot, Update event) throws TelegramApiException {
        Long chatId = event.getMessage().getChatId();

        SendMessage request = new SendMessage()
                .setChatId(chatId)
                .setText(HELP_MESSAGE);

        bot.executeAsync(request, new BaseSentCallback<>());
    }
}

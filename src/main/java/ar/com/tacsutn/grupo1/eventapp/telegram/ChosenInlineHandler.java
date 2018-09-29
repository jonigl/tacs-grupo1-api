package ar.com.tacsutn.grupo1.eventapp.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChosenInlineHandler {

    /**
     * Handles an incoming message containing a URL.
     * @param bot the Telegram bot.
     * @param update the update containing the URL message.
     */
    public void handle(TelegramBot bot, Update update) {
        List<MessageEntity> entities = update.getMessage().getEntities();
        Optional<String> eventId = getEventIdFromEntities(entities);
        eventId.ifPresent(id -> addReplyMarkup(bot, update, id));
    }

    /**
     * Sends the edit message request.
     * @param bot the Telegram bot.
     * @param update the update containing the message to edit.
     * @param eventId the Eventbrite event id parsed from URL.
     */
    private void addReplyMarkup(TelegramBot bot, Update update, String eventId) {
        try {
//            bot.executeAsync(getEditMessageRequest(update, eventId), new BaseSentCallback<>());
            bot.execute(getEditMessageRequest(update, eventId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the edit message request to be sent by the bot.
     * @param update the update message.
     * @param eventId the Eventbrite event id parsed from URL.
     * @return the edit message request.
     */
    private EditMessageReplyMarkup getEditMessageRequest(Update update, String eventId) {
        return new EditMessageReplyMarkup()
            .setInlineMessageId(update.getChosenInlineQuery().getInlineMessageId())
            .setReplyMarkup(createReplyMarkup(update, eventId));
    }

    /**
     * Creates the reply markup of the URL message with inline keyboard button.
     * @param eventId the Eventbrite event id parsed from URL.
     * @return the specified reply markup.
     */
    private InlineKeyboardMarkup createReplyMarkup(Update update, String eventId) {
        CallbackData callbackData = new CallbackData();
        callbackData.setChatId(update.getMessage().getChatId());
        callbackData.setEventId(eventId);

        InlineKeyboardButton addButton = new InlineKeyboardButton("Añadir a una lista");
        addButton.setCallbackData(CallbackData.serialize(callbackData));

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(addButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        return markup;
    }

    /**
     * Extracts an Eventbrite event id from message entities if exists.
     * @param entities the message entities.
     * @return the event id.
     */
    protected Optional<String> getEventIdFromEntities(List<MessageEntity> entities) {
        return entities.stream()
            .filter(entity -> entity.getType().equals("text_link"))
            .findFirst()
            .flatMap(entity -> getEventIdFromUrl(entity.getUrl()));
    }

    protected Optional<String> getEventIdFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            if (checkProtocol(url.getProtocol()) && checkHost(url.getAuthority())) {
                return getEventIdFromPath(url.getPath());
            } else {
                return Optional.empty();
            }
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

    private boolean checkProtocol(String protocol) {
        return protocol.equals("https");
    }

    private boolean checkHost(String host) {
        String regex = "^www\\.eventbrite(\\.com)?\\.[a-zA-Z]{2,3}$";
        return host.matches(regex);
    }

    private Optional<String> getEventIdFromPath(String path) {
        String[] segments = path.split("/");

        if (segments.length == 3
                && segments[0].isEmpty()
                && segments[1].equals("e")) {

            String[] parts = segments[2].split("-");
            return Optional.of(parts[parts.length - 1]);
        } else {
            return Optional.empty();
        }
    }
}

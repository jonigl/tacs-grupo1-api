package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.CallbackData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Creates the reply markup of the event details message with inline keyboard button.
     * @param event the Eventbrite event of the message.
     * @return the specified reply markup.
     */
    public static InlineKeyboardMarkup createEventDetailsReplyMarkup(Event event) {
        CallbackData callbackData = new CallbackData();
        callbackData.setType(CallbackData.Type.SELECTED_EVENT);
        callbackData.setEventId(event.getId());

        InlineKeyboardButton linkButton = new InlineKeyboardButton("Ver en Eventbrite");
        linkButton.setUrl(event.getUrl());

        InlineKeyboardButton addButton = new InlineKeyboardButton("Añadir a una lista");
        addButton.setCallbackData(CallbackData.serialize(callbackData));

        List<InlineKeyboardButton> linkRow = new ArrayList<>();
        linkRow.add(linkButton);

        List<InlineKeyboardButton> addRow = new ArrayList<>();
        addRow.add(addButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(linkRow);
        rows.add(addRow);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        return markup;
    }
}

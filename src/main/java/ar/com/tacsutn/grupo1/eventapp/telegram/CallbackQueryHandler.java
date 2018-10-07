package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.telegram.callback.CallbackData;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation.SelectedEventCallbackOperation;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation.SelectedListCallbackOperation;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation.ShowEventDetailsCallbackOperation;
import ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation.ShowListEventsCallbackOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackQueryHandler {

    private final SelectedEventCallbackOperation selectedEventCallbackOperation;
    private final SelectedListCallbackOperation selectedListCallbackOperation;
    private final ShowListEventsCallbackOperation showListEventsCallbackOperation;
    private final ShowEventDetailsCallbackOperation showEventDetailsCallbackOperation;

    @Autowired
    public CallbackQueryHandler(
            SelectedEventCallbackOperation selectedEventCallbackOperation,
            SelectedListCallbackOperation selectedListCallbackOperation,
            ShowListEventsCallbackOperation showListEventsCallbackOperation,
            ShowEventDetailsCallbackOperation showEventDetailsCallbackOperation) {

        this.selectedEventCallbackOperation = selectedEventCallbackOperation;
        this.selectedListCallbackOperation = selectedListCallbackOperation;
        this.showListEventsCallbackOperation = showListEventsCallbackOperation;
        this.showEventDetailsCallbackOperation = showEventDetailsCallbackOperation;
    }

    /**
     * Processes a callback query triggered by an inline button.
     * @param bot the telegram bot.
     * @param update the update event containing the callback query.
     */
    public void handle(TelegramBot bot, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        CallbackData.deserialize(callbackQuery.getData())
                    .ifPresent(callbackData -> dispatchOperation(bot, callbackQuery, callbackData));
    }

    /**
     * Dispatches an operation by its type and executes it.
     * @param bot the Telegram bot.
     * @param callbackQuery the original callback query.
     * @param callbackData the callback query data with operation information.
     */
    private void dispatchOperation(TelegramBot bot, CallbackQuery callbackQuery, CallbackData callbackData) {
        switch (callbackData.getType()) {
            case SELECTED_EVENT:
                selectedEventCallbackOperation.handle(bot, callbackQuery, callbackData);
                break;
            case SELECTED_LIST:
                selectedListCallbackOperation.handle(bot, callbackQuery, callbackData);
                break;
            case SHOW_LIST_EVENTS:
                showListEventsCallbackOperation.handle(bot, callbackQuery, callbackData);
                break;
            case SHOW_EVENT_DETAILS:
                showEventDetailsCallbackOperation.handle(bot, callbackQuery, callbackData);
                break;
        }
    }
}

package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.telegram.BaseSentCallback;
import ar.com.tacsutn.grupo1.eventapp.telegram.CallbackData;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyListsCommand implements BotCommand {

    private final TelegramUserRepository telegramUserRepository;
    private final EventListService eventListService;

    @Autowired
    public MyListsCommand(TelegramUserRepository telegramUserRepository, EventListService eventListService) {
        this.telegramUserRepository = telegramUserRepository;
        this.eventListService = eventListService;
    }

    @Override
    public void run(TelegramBot bot, Update event) throws TelegramApiException {
        Integer userId = event.getMessage().getFrom().getId();

        SendMessage request = telegramUserRepository.getByTelegramUserId(userId)
            .map(user -> eventListService.getListsByUser(user.getInternalUser()))
            .map(lists -> getShowListMessageRequestFromEventLists(event, lists))
            .orElse(getRequestLoginMessageRequest(event));

        bot.executeAsync(request, new BaseSentCallback<>());
    }

    private SendMessage getShowListMessageRequestFromEventLists(Update event, Page<EventList> eventLists) {
        return new SendMessage()
                .setChatId(event.getMessage().getChatId())
                .setText("Seleccione una lista de eventos:")
                .setReplyMarkup(getKeyboardMarkupFromEventLists(eventLists));
    }

    private SendMessage getRequestLoginMessageRequest(Update event) {
        return new SendMessage()
                .setChatId(event.getMessage().getChatId())
                .setText("Debe loguearse primero.");
    }

    private InlineKeyboardMarkup getKeyboardMarkupFromEventLists(Page<EventList> eventLists) {
        return new InlineKeyboardMarkup()
                .setKeyboard(getKeyboardFromEventLists(eventLists));
    }

    private List<List<InlineKeyboardButton>> getKeyboardFromEventLists(Page<EventList> eventLists) {
        return eventLists.stream().parallel()
                .map(this::getKeyboardButtonFromEventList)
                .map(Collections::singletonList)
                .collect(Collectors.toList());
    }

    private InlineKeyboardButton getKeyboardButtonFromEventList(EventList eventList) {
        CallbackData callbackData = new CallbackData();
        callbackData.setType(CallbackData.Type.SHOW_LIST_EVENTS);
        callbackData.setListId(eventList.getId());

        return new InlineKeyboardButton()
                .setText(eventList.getName())
                .setCallbackData(CallbackData.serialize(callbackData));
    }
}

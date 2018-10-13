package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.UserService;
import ar.com.tacsutn.grupo1.eventapp.telegram.BaseSentCallback;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AuthenticateCommand implements BotCommand {

    @Value("${telegram.auth-page-url}")
    private String AUTH_PAGE_URL;

    private final UserService userService;

    @Autowired
    public AuthenticateCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(TelegramBot bot, Update event) throws TelegramApiException {
        Integer userId = event.getMessage().getFrom().getId();

        String message = userService.getByTelegramUserId(userId)
                .map(this::getAuthenticatedMessageRequest)
                .orElseGet(this::getUnauthenticatedMessageRequest);

        SendMessage request = new SendMessage()
                .setChatId(event.getMessage().getChatId())
                .setText(message);

        bot.executeAsync(request, new BaseSentCallback<>());
    }

    private String getAuthenticatedMessageRequest(User user) {
        return "Estás autenticado como ***'" + user.getUsername() + "'***";
    }

    private String getUnauthenticatedMessageRequest() {
        return "Entrar aquí para autenticarse: " + AUTH_PAGE_URL;
    }
}

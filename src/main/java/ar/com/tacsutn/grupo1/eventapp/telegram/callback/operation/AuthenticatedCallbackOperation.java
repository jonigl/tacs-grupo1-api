package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.UserService;
import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

abstract class AuthenticatedCallbackOperation extends AbstractCallbackOperation {

    private final UserService userService;

    protected AuthenticatedCallbackOperation(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the specified user or sends a login request alert if unauthenticated.
     * @param bot the Telegram bot.
     * @param callbackQuery the callback query required authentication.
     * @return the authenticated user.
     */
    protected Optional<User> getUserOrAlert(TelegramBot bot, CallbackQuery callbackQuery) {
        Integer userId = callbackQuery.getFrom().getId();
        Optional<User> user = userService.getByTelegramUserId(userId);

        if (!user.isPresent()) {
            AnswerCallbackQuery answer = answerUnauthenticated(callbackQuery);
            makeRequest(bot, answer);
        }

        return user;
    }

    /**
     * Creates the login request alert to an unauthenticated operation.
     * @param callbackQuery the callback query with unauthenticated operation.
     * @return the answer request.
     */
    private AnswerCallbackQuery answerUnauthenticated(CallbackQuery callbackQuery) {
        return new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setText("Debe loguearse primero.")
                .setShowAlert(true);
    }
}

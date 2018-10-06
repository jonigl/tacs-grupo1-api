package ar.com.tacsutn.grupo1.eventapp.telegram.callback.operation;

import ar.com.tacsutn.grupo1.eventapp.telegram.TelegramBot;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUser;
import ar.com.tacsutn.grupo1.eventapp.telegram.user.TelegramUserRepository;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

abstract class AuthenticatedCallbackOperation extends AbstractCallbackOperation {

    private final TelegramUserRepository telegramUserRepository;

    protected AuthenticatedCallbackOperation(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    /**
     * Retrieves the specified Telegram user or sends a login request alert if unauthenticated.
     * @param bot the Telegram bot.
     * @param callbackQuery the callback query required authentication.
     * @return the Telegram user.
     */
    protected Optional<TelegramUser> getUserOrAlert(TelegramBot bot, CallbackQuery callbackQuery) {
        Integer userId = callbackQuery.getFrom().getId();
        Optional<TelegramUser> telegramUser = telegramUserRepository.getByTelegramUserId(userId);
        if (!telegramUser.isPresent()) {
            AnswerCallbackQuery answer = answerUnauthenticated(callbackQuery);
            makeRequest(bot, answer);
        }

        return telegramUser;
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

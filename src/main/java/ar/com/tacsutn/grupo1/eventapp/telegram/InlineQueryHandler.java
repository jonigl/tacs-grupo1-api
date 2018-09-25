package ar.com.tacsutn.grupo1.eventapp.telegram;

import ar.com.tacsutn.grupo1.eventapp.client.EventFilter;
import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InlineQueryHandler {

    private final EventbriteClient eventbriteClient;

    @Autowired
    public InlineQueryHandler(EventbriteClient eventbriteClient) {
        this.eventbriteClient = eventbriteClient;
    }

    /**
     * Processes an inline query from a user.
     * @param bot the telegram bot.
     * @param update the update event containing inline query.
     */
    public void handle(TelegramBot bot, Update update) {
        InlineQuery inlineQuery = update.getInlineQuery();

        try {
            bot.executeAsync(createAnswer(inlineQuery), new BaseSentCallback<>());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the inline answer with Eventbrite search result.
     * @param inlineQuery the inline query.
     * @return the inline query answer.
     */
    private AnswerInlineQuery createAnswer(InlineQuery inlineQuery) {
        EventFilter filter = new EventFilter().setKeyword(inlineQuery.getQuery());

        List<InlineQueryResult> results = eventbriteClient
                .searchEvents(filter)
                .map(this::createResults)
                .orElseGet(Collections::emptyList);

        return new AnswerInlineQuery()
                .setInlineQueryId(inlineQuery.getId())
                .setResults(results);
    }

    /**
     * Converts an Eventbrite search result page to an Telegram inline query result list.
     * @param page the Eventbrite search result page.
     * @return an Telegram inline query result list.
     */
    private List<InlineQueryResult> createResults(Page<Event> page) {
        return page.getContent()
                .subList(0, page.getNumberOfElements() > 5 ? 5 : page.getNumberOfElements())
                .parallelStream()
                .map(this::createResult)
                .collect(Collectors.toList());
    }

    /**
     * Converts an Eventbrite event model to a Telegram inline query result.
     * @param event the Eventbrite event.
     * @return a Telegram inline query result.
     */
    private InlineQueryResult createResult(Event event) {
        return new InlineQueryResultArticle()
                .setId(event.getId())
                .setTitle(event.getName())
                .setDescription(event.getDescription())
                .setInputMessageContent(createMessageContent(event));
    }

    /**
     * Creates a formatted message content to be sent when the user
     *  selects the event from search results.
     * @param event the Eventbrite event.
     * @return the message content.
     */
    private InputTextMessageContent createMessageContent(Event event) {
        String description = event.getDescription();
        if (description.length() > 500) {
            description = description.substring(0, 500) + "...";
        }

        String messageText = new StringBuilder()
                .append("***").append(event.getName()).append("***\n")
                .append("\n")
                .append(description)
                .toString();

        return new InputTextMessageContent()
                .setMessageText(messageText)
                .enableMarkdown(true);
    }
}

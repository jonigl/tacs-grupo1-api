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
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
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

        String query = inlineQuery.getQuery();
        if (query != null && !query.isEmpty()) {
            try {
                bot.executeAsync(createAnswer(inlineQuery), new BaseSentCallback<>());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates the inline answer with Eventbrite search result.
     * @param inlineQuery the inline query.
     * @return the inline query answer.
     */
    private AnswerInlineQuery createAnswer(InlineQuery inlineQuery) {
        String offset = inlineQuery.getOffset();
        Integer offsetNumber;

        if (offset == null || offset.isEmpty()) {
            offsetNumber = 0;
        } else {
            offsetNumber = Integer.parseInt(offset);
        }

        Integer pageNumber = offsetNumber / 50;
        Integer pageOffset = offsetNumber % 50;

        EventFilter filter = new EventFilter().setKeyword(inlineQuery.getQuery());

        List<InlineQueryResult> results = eventbriteClient
                .searchEvents(filter, pageNumber)
                .map(page -> this.createResults(page, pageOffset))
                .orElseGet(Collections::emptyList);

        return new AnswerInlineQuery()
                .setInlineQueryId(inlineQuery.getId())
                .setPersonal(false)
                .setResults(results)
                .setNextOffset(Integer.toString(offsetNumber + 5));
    }

    /**
     * Converts an Eventbrite search result page to an Telegram inline query result list.
     * @param page the Eventbrite search result page.
     * @return an Telegram inline query result list.
     */
    private List<InlineQueryResult> createResults(Page<Event> page, int fromIndex) {
        int elements = page.getNumberOfElements();
        int remainingElements = Math.max(elements - fromIndex, 0);
        int fetchElements = Math.min(remainingElements, 5);
        int toIndex = fromIndex + fetchElements;

        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }

        return page.getContent()
                .subList(fromIndex, toIndex)
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
        String description = event.getDescription();
        if (description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }

        return new InlineQueryResultArticle()
                .setId(event.getId())
                .setTitle(event.getName())
                .setDescription(description)
                .setThumbUrl(event.getLogo())
                .setUrl(event.getUrl())
                .setInputMessageContent(createMessageContent(event))
                .setReplyMarkup(Utils.createEventDetailsReplyMarkup(event));
    }

    /**
     * Creates a formatted message content to be sent when the user
     *  selects the event from search results.
     * @param event the Eventbrite event.
     * @return the message content.
     */
    private InputMessageContent createMessageContent(Event event) {
        String messageText = new StringBuilder()
                .append("Has seleccionado el evento ")
                .append("[").append(event.getName()).append("]")
                .append("(").append(event.getUrl()).append(").")
                .toString();

        return new InputTextMessageContent()
                .setMessageText(messageText)
                .enableMarkdown(true);
    }
}

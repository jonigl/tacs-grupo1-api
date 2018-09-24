package ar.com.tacsutn.grupo1.eventapp.telegram.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

@Component
public class BotCommandFactory {

    private Map<String, BotCommand> botCommandMap;

    @Autowired
    public BotCommandFactory(ApplicationContext applicationContext) {
        botCommandMap = new Hashtable<>();
        botCommandMap.put("/search", applicationContext.getBean(SearchCommand.class));
    }

    public Optional<BotCommand> getCommand(String commandName) {
        return Optional.ofNullable(botCommandMap.get(commandName));
    }
}

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
        botCommandMap.put("/start", applicationContext.getBean(HelpCommand.class));
        botCommandMap.put("/authenticate", applicationContext.getBean(AuthenticateCommand.class));
        botCommandMap.put("/search", applicationContext.getBean(SearchCommand.class));
        botCommandMap.put("/mylists", applicationContext.getBean(MyListsCommand.class));
        botCommandMap.put("/help", applicationContext.getBean(HelpCommand.class));
    }

    /**
     * Retrieves a bot command by the command name.
     * @param commandName the command name.
     * @return the requested bot command.
     */
    public Optional<BotCommand> getCommand(String commandName) {
        return Optional.ofNullable(botCommandMap.get(commandName));
    }
}

package com.softage.command;

import com.softage.repository.ChatServerSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

@Component
public class StopCommand extends BotCommand {

    @Autowired
    private ChatServerSubscriptionRepository chatServerSubscriptionRepository;


    public StopCommand() {
        super("stop", "Stop the World");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        chatServerSubscriptionRepository.deleteByServerIdAndChatId("5b37a33f05930055d2f1bc56", chat.getId());

    }
}

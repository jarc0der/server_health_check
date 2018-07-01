package com.softage.command;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

public class HelloCommand extends BotCommand {

    public HelloCommand() {
        super("hello", "description");
    }

    public void execute( AbsSender absSender, User user, Chat chat, String[] strings ) {
        System.out.println("/hello executes");
    }
}

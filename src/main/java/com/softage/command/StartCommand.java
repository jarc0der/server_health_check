package com.softage.command;

import com.softage.domain.ChatState;
import com.softage.domain.Subscription;
import com.softage.repository.ChatServerSubscriptionRepository;
import com.softage.repository.CurrentChatStateRepository;
import com.softage.utils.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

@Component
public class StartCommand extends BotCommand {

    @Autowired
    private CurrentChatStateRepository currentChatStateRepository;

    @Autowired
    private ChatServerSubscriptionRepository chatServerSubscriptionRepository;

    public StartCommand() {
        super("start", "Start interaction with HealthBot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        ChatState currentChat = currentChatStateRepository.findByChatId(chat.getId());

        if(currentChat == null){
            ChatState newChatState = new ChatState(null, chat.getId(), user.getUserName(), user.getFirstName(), user.getLastName()) ;
            currentChatStateRepository.save(newChatState);
            System.out.println("Save new chat id" + newChatState);

            //create subscription
            Subscription subscription = new Subscription(null, "5b37a33f05930055d2f1bc56", chat.getId(), TimeTools.nowUTC());
            chatServerSubscriptionRepository.save(subscription);

            System.out.println("Chat id " + chat.getId() + " was subscribed to the server");
        }
    }
}

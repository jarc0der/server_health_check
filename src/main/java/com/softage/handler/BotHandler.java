package com.softage.handler;

import com.softage.command.HelloCommand;
import com.softage.command.StartCommand;
import com.softage.command.StopCommand;
import com.softage.domain.ServerState;
import com.softage.domain.ServerStatus;
import com.softage.domain.StatusCheckResult;
import com.softage.domain.Subscription;
import com.softage.repository.ChatServerSubscriptionRepository;
import com.softage.repository.CurrentServerStatusStateRepository;
import com.softage.service.StatusChecker;
import com.softage.utils.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Component
public class BotHandler extends TelegramLongPollingCommandBot {

    //We have to do this, to Autowire StartCommand services
    @Autowired
    private StartCommand startCommand;

    @Autowired
    private StopCommand stopCommand;

    private static final int TIME = 1000 * 60;

    private static final int TIME_DELAY = 2000 * 60;

    @Autowired
    private StatusChecker statusChecker;

    @Autowired
    private CurrentServerStatusStateRepository currentServerStatusStateRepository;

    @Autowired
    private ChatServerSubscriptionRepository chatServerSubscriptionRepository;


    @PostConstruct
    public void init(){
        register(new HelloCommand());
        register(startCommand);
        register(stopCommand);
    }

    @Inject
    public BotHandler( @Value("HealthCheckBot") String botName){
        super(botName);

    }

    @Scheduled(initialDelay = 1000, fixedRate=TIME)
    public void asyncCallMethod(){
        System.out.println("Let's check server states.");

        List<ServerStatus> allServers = currentServerStatusStateRepository.findAll();

        allServers.removeIf(s -> s.getCheckDelay() == 1 && TimeTools.nowUTC() - s.getLastOfflineDate() <= TIME_DELAY );

        for(ServerStatus serverStatus : allServers){
            //this service doesn't work with DB, it's internal service with custom impl. to get status via http.
            StatusCheckResult currentServerStatus = statusChecker.checkStatus( allServers.get(0) );
            StatusCheckResult result = updateServerStatus(serverStatus, currentServerStatus);
            List<Subscription> subscriptions = chatServerSubscriptionRepository.findByServerId(serverStatus.get_id());

            if(result != null){
                sendNotification(subscriptions, serverStatus, result);
            }

            //update server state in the DB
            currentServerStatusStateRepository.save(serverStatus);
        }

    }

    private void sendNotification( List<Subscription> subscriptions, ServerStatus serverStatus, StatusCheckResult result){
        StringBuilder message = new StringBuilder();
        message.append("Server is <b>" + result.getServerState() + "</b> now.\n")
        .append("Server: " + serverStatus.getName() + "\n")
                .append("Admin: @tuzhanskyi");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message.toString());
        sendMessage.setParseMode("html");

        for(Subscription subscription : subscriptions){
            sendMessage.setChatId(subscription.getChatId());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    private StatusCheckResult updateServerStatus( ServerStatus serverStatus, StatusCheckResult currentServerStatus ) {
        boolean sendNotification = false;

        if(currentServerStatus.getServerState() == ServerState.ON){
            if(serverStatus.getState() == ServerState.OFF){
                //create notification about enabling the server.
                sendNotification = true;
                System.out.println("NTF: server is available now!");
                serverStatus.setCheckDelay(0);
            }
            serverStatus.setLastOnlineDate(currentServerStatus.getDate());
            serverStatus.setState(currentServerStatus.getServerState());

        }else if(currentServerStatus.getServerState() == ServerState.OFF){
            if(serverStatus.getState() == ServerState.ON){
                //create notification about disabling the server.
                sendNotification = true;
                System.out.println("NTF: server is shutdown now!");
            }
            serverStatus.setLastOfflineDate(currentServerStatus.getDate());
            serverStatus.setState(currentServerStatus.getServerState());
            serverStatus.setCheckDelay(1);
        }else {
            //throw exception because we receive unknown or NONE serverState
        }

        return sendNotification ? currentServerStatus : null;
    }

    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text == null ? "missed number" : text);
            try {
                execute(response);
//                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
//                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    public String getBotToken() {
        return "606008094:AAGPXyI0By-G0Yl5ksnCcDHUgnCiQbOCqHs";
    }
}

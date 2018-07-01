package com.softage.repository;

import com.softage.domain.ChatState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentChatStateRepository extends MongoRepository<ChatState, String> {

    ChatState findByChatId(long chatId);

}

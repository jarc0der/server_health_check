package com.softage.repository;

import com.softage.domain.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatServerSubscriptionRepository extends MongoRepository<Subscription, String> {

    List<Subscription> findByServerId(String serverId);

    void deleteByServerIdAndChatId(String serverId, long chatId);
}

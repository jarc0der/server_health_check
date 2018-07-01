package com.softage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscriptions")
@Getter
@Setter
@AllArgsConstructor
public class Subscription {

    @Id
    private String _id;

    private String serverId;

    private long chatId;

    private long date;

}

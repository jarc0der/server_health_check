package com.softage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatStates")
@Getter
@Setter
@AllArgsConstructor
public class ChatState {

    @Id
    private String _id;

    private long chatId;

    private String userName;

    private String firstName;

    private String secondName;

}

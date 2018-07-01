package com.softage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "server")
public class ServerStatus {

    @Id
    private String _id;

    private String name;

    private String domain;

    //a collection of online dates
    private long lastOnlineDate;

    //a collection of offline dates
    private long lastOfflineDate;

    //current status
    private ServerState state;

    //current check delay
    //specially use for offline services to ignore them
    private int checkDelay = 0;

}

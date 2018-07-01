package com.softage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusCheckResult {

    //service id
    private String serverId;

    //check data
    private ServerState serverState;

    //timestamp
    private long date;

}

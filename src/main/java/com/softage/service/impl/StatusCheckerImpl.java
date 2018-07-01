package com.softage.service.impl;

import com.softage.domain.ServerState;
import com.softage.domain.ServerStatus;
import com.softage.domain.StatusCheckResult;
import com.softage.service.StatusChecker;
import com.softage.utils.TimeTools;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class StatusCheckerImpl implements StatusChecker {
    @Override
    public StatusCheckResult checkStatus(ServerStatus serverStatus) {

        StatusCheckResult result = new StatusCheckResult(serverStatus.get_id(), new Random().nextBoolean() ? ServerState.ON : ServerState.OFF, TimeTools.nowUTC());

        return result;
    }
}

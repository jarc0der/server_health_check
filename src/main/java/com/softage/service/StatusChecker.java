package com.softage.service;

import com.softage.domain.ServerStatus;
import com.softage.domain.StatusCheckResult;

public interface StatusChecker {

    StatusCheckResult checkStatus(ServerStatus serverStatus);

}

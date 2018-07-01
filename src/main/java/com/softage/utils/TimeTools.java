package com.softage.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeTools {

    public static long nowUTC(){
        return LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

}

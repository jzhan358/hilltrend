package com.third.hilltrend.api.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
@Slf4j
public final class LocalUtils {

    private LocalUtils(){

    }

    public static String hostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
//            log.warn("" + e.getMessage());
        }
        return "未知IP";

    }
}

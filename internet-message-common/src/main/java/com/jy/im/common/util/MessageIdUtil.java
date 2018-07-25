package com.jy.im.common.util;

import java.util.concurrent.atomic.AtomicInteger;

public class MessageIdUtil {

    public static int generateLoginMessageId() {
        return LoginMessageId.id.incrementAndGet();
    }

    private static class LoginMessageId {
        private static final AtomicInteger id = new AtomicInteger(0);
    }

}

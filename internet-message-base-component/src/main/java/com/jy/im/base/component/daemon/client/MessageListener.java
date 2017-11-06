package com.jy.im.base.component.daemon.client;

import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageListener<Message> {

    private final TypeParameterMatcher matcher;

    public abstract void callback(Message message);

    public MessageListener() {
        matcher = TypeParameterMatcher.find(this, MessageListener.class, "Message");
    }

    public boolean apply(Object message) {
        return matcher.match(message);
    }

}

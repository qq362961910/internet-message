package com.jy.im.base.component.daemon.client;

import io.netty.util.internal.TypeParameterMatcher;

public abstract class AbstractMessageListener<Message> implements MessageListener<Message> {

    private final TypeParameterMatcher matcher;

    public AbstractMessageListener() {
        matcher = TypeParameterMatcher.find(this, AbstractMessageListener.class, "Message");
    }

    public boolean apply(Object message) {
        return matcher.match(message);
    }

}

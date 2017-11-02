package com.jy.im.base.component.daemon.listener;

import com.jy.im.base.component.analyser.protocol.AbstractMessageProtocolAnalyser;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageListener<Message> {

    private final TypeParameterMatcher matcher;

    public abstract void callback(Message message);

    public MessageListener() {
        matcher = TypeParameterMatcher.find(this, AbstractMessageProtocolAnalyser.class, "Message");
    }

    public boolean apply(Object message) {
        return matcher.match(message);
    }

}

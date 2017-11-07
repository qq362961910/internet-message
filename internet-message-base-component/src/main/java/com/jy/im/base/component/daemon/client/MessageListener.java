package com.jy.im.base.component.daemon.client;

public interface MessageListener<Message> {
    boolean apply(Object message);
    void callback(Message message);
}

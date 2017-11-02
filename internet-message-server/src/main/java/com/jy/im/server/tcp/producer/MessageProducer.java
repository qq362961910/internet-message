package com.jy.im.server.tcp.producer;

public interface MessageProducer<In> {
    Object produce(In in);
}

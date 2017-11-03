package com.jy.im.server.tcp.analyser;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyserManager;
import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NettyMessageAnalyserManager extends AbstractMessageAnalyserManager<ByteBuf> {

    @Autowired
    private DefaultNettyMessageAnalyser nettyCommonMessageAnalyser;

    @PostConstruct
    public void init() {
        addMessageAnalyser(nettyCommonMessageAnalyser);
    }

}

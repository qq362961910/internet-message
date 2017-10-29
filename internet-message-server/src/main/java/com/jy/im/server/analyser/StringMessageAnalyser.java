package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StringMessageAnalyser extends NettyMessageAnalyser<String> {

    @Autowired
    private NettyStringMessageProtocolAnalyser nettyStringMessageProtocolAnalyser;

    @Override
    public String analyse(ByteBuf t) {
        byte[] content = new byte[t.readableBytes()];
        t.readBytes(content);
        return new String(content);
    }

    @Override
    public ProtocolAnalyser<ByteBuf> getProtocolAnalyser() {
        return nettyStringMessageProtocolAnalyser;
    }
}

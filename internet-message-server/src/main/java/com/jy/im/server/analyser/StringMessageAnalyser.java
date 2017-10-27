package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.base.component.enums.MessageProtocol;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class StringMessageAnalyser extends NettyMessageAnalyser<String> {

    private NettyStringMessageProtocolAnalyser nettyStringMessageProtocolAnalyser = new NettyStringMessageProtocolAnalyser();

    @Override
    public String analyse(ByteBuf t) {
        byte[] content = new byte[t.readableBytes()];
        t.readBytes(content);
        return new String(content);
    }

    public StringMessageAnalyser() {
        super(MessageProtocol.STRING);
    }

    @Override
    public ProtocolAnalyser<ByteBuf> getProtocolAnalyser() {
        return nettyStringMessageProtocolAnalyser;
    }
}

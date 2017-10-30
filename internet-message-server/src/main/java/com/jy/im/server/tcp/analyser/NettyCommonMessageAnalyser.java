package com.jy.im.server.tcp.analyser;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.common.entity.CommonMessage;
import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyCommonMessageAnalyser extends NettyMessageAnalyser {

    @Autowired
    private NettyCommonMessageProtocolAnalyser nettyCommonMessageProtocolAnalyser;

    @Override
    public CommonMessage analyse(ByteBuf in) {
        if(in.readableBytes() < 3) {
            return null;
        }
        //1 byte
        byte messageType = in.readByte();
        //2 bytes
        short length = in.readShort();
        if(in.readableBytes() < length) {
            return null;
        }
        //3. content
        byte[] content = new byte[length];
        in.readBytes(content);

        //package the message
        CommonMessage message = new CommonMessage();
        message.setMessageType(messageType);
        message.setLength(length);
        message.setContent(content);
        return message;
    }

    @Override
    public ProtocolAnalyser<ByteBuf> getProtocolAnalyser() {
        return nettyCommonMessageProtocolAnalyser;
    }
}

package com.jy.im.server.tcp.analyser;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.base.component.translator.CommonMessageMessageTranslator;
import com.jy.im.base.component.translator.LoginCommonMessageTranslator;
import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageType;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultNettyMessageAnalyser extends NettyMessageAnalyser {

    private static final Logger logger = LoggerFactory.getLogger(DefaultNettyMessageAnalyser.class);

    @Autowired
    private NettyCommonMessageProtocolAnalyser nettyCommonMessageProtocolAnalyser;

    private List<MessageTranslator<ByteBuf>> messageProducerList = new ArrayList<>();

    @Override
    public Object analyse(ByteBuf in) {
        short length = in.readShort();
        if (in.readableBytes() < length) {
            return null;
        }
        MessageType messageType = MessageType.getCommonMessageType(in.readByte());
        for (MessageTranslator<ByteBuf> translator : messageProducerList) {
            if (translator.support(messageType)) {
                return translator.translate(in);
            }
        }
        logger.warn("no message found");
        return null;
    }

    @Override
    public ProtocolAnalyser<ByteBuf> getProtocolAnalyser() {
        return nettyCommonMessageProtocolAnalyser;
    }

    public void addMessageTranslator(MessageTranslator<ByteBuf> translator) {
        if (translator != null) {
            messageProducerList.add(translator);
        }
    }

    public DefaultNettyMessageAnalyser() {
        addMessageTranslator(new LoginCommonMessageTranslator());
        addMessageTranslator(new CommonMessageMessageTranslator());
    }
}

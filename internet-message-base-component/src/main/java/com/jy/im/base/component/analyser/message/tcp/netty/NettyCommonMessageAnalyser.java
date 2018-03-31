package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.exception.NoMessageTranslatorFoundException;
import com.jy.im.base.component.exception.UnsupportedMessageTypeException;
import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;
import io.netty.buffer.ByteBuf;

/**
 * netty common message analyser
 * */
public class NettyCommonMessageAnalyser extends NettyMessageAnalyser {

    @Override
    public MessageProtocol supportMessageProtocol() {
        return MessageProtocol.COMMON;
    }

    @Override
    public Object analyse(ByteBuf in) throws UnsupportedMessageTypeException, NoMessageTranslatorFoundException {
        //消息种类
        byte messageType = in.readByte();
        MessageType type = MessageType.getCommonMessageType(messageType);
        if(type == null) {
            throw new UnsupportedMessageTypeException("message type: " + messageType);
        }
        //找到具体消息转换器解析消息
        MessageTranslator<ByteBuf> translator = getMessageTranslator(type);
        if(translator == null) {
            throw new NoMessageTranslatorFoundException("message type: " + messageType);
        }
        return translator.translate(in);
    }
}

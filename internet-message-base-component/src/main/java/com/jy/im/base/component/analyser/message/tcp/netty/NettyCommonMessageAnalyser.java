package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.exception.NoMessageTranslatorFoundException;
import com.jy.im.base.component.exception.UnsupportedMessageContentTypeException;
import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageContentType;
import io.netty.buffer.ByteBuf;

/**
 * netty common message analyser
 *
 * COMMON协议数据结构
 * message type: 1 byte
 * message body: n byes
 *
 * */
public class NettyCommonMessageAnalyser extends NettyMessageAnalyser {

    @Override
    public MessageProtocol supportMessageProtocol() {
        return MessageProtocol.COMMON;
    }

    @Override
    public Object analyse(ByteBuf in) throws UnsupportedMessageContentTypeException, NoMessageTranslatorFoundException {
        //消息种类
        byte messageContentType = in.readByte();
        MessageContentType type = MessageContentType.getMessageContentType(messageContentType);
        if(type == null) {
            throw new UnsupportedMessageContentTypeException("message content type: " + messageContentType);
        }
        //找到具体消息转换器解析消息
        MessageTranslator<ByteBuf> translator = getMessageTranslator(supportMessageProtocol(), type);
        if(translator == null) {
            throw new NoMessageTranslatorFoundException("message content type: " + messageContentType);
        }
        return translator.translate(in);
    }
}

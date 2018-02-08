package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyser;
import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.base.component.analyser.protocol.netty.NettyMessageProtocolAnalyser;
import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NettyMessageAnalyser extends AbstractMessageAnalyser<ByteBuf> {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(NettyMessageAnalyser.class);

    private NettyMessageProtocolAnalyser nettyMessageProtocolAnalyser;

    private List<MessageTranslator<ByteBuf>> messageTranslators = new ArrayList<>();

    private int length = -1;
    private byte mt = -1;

    @Override
    public Object analyse(ByteBuf in) {
        //读取消息长度
        if(length == -1) {
            length = in.readShort();
        }
        //消息不完整
        if (in.readableBytes() < length) {
            return null;
        }
        mt = in.readByte();
        //消息种类
        MessageType messageType = MessageType.getCommonMessageType(mt);
        if(messageType == null) {
            logger.error("unknown message type: {}", messageType);
        }
        for (MessageTranslator<ByteBuf> translator : messageTranslators) {
            if (translator.support(messageType)) {
                return translator.translate(in);
            }
        }
        logger.error("no translator found for messageType: {}", messageType);
        return null;
    }

    @Override
    public ProtocolAnalyser<ByteBuf> getProtocolAnalyser() {
        return nettyMessageProtocolAnalyser;
    }

    public void addMessageTranslator(MessageTranslator<ByteBuf>... messageTranslators) {
        if (messageTranslators != null && messageTranslators.length > 0) {
            this.messageTranslators.addAll(Arrays.asList(messageTranslators));
        }
    }

    public NettyMessageProtocolAnalyser getNettyMessageProtocolAnalyser() {
        return nettyMessageProtocolAnalyser;
    }

    public void setNettyMessageProtocolAnalyser(NettyMessageProtocolAnalyser nettyMessageProtocolAnalyser) {
        this.nettyMessageProtocolAnalyser = nettyMessageProtocolAnalyser;
    }
}

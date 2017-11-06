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

    @Override
    public Object analyse(ByteBuf in) {
        short length = in.readShort();
        if (in.readableBytes() < length) {
            return null;
        }
        MessageType messageType = MessageType.getCommonMessageType(in.readByte());
        for (MessageTranslator<ByteBuf> translator : messageTranslators) {
            if (translator.support(messageType)) {
                return translator.translate(in);
            }
        }
        logger.warn("no message found");
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

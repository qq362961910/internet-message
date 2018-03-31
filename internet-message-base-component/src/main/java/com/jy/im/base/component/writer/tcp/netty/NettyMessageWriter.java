package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

public abstract class NettyMessageWriter <Entity extends BaseMessage> extends MessageToByteEncoder<Entity> {

    private static final InternalLogger logger = Slf4JLoggerFactory.getInstance(NettyMessageWriter.class);

    private byte lengthBytes = 2;

    @Override
    protected void encode(ChannelHandlerContext ctx, Entity msg, ByteBuf out) throws Exception {
        int beginIndex = out.writerIndex();
        //长度占位 2 bytes
        out.writeZero(lengthBytes);
        //protocol 1 byte
        out.writeByte(msg.getProtocol());
        //消息类型 1 byte
        out.writeByte(msg.getMessageType());
        //子类写出数据
        doEncode(ctx, msg, out);
        int endIndex = out.writerIndex();
        //指针定位
        out.writerIndex(beginIndex);
        //写出长度
        out.writeShort(endIndex - beginIndex - lengthBytes);
        //回复指针
        out.writerIndex(endIndex);
    }
    public abstract void doEncode(ChannelHandlerContext ctx, Entity msg, ByteBuf buf);

}

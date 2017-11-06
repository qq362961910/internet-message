package com.jy.im.base.component.decoder.tcp.netty;

import com.jy.im.base.component.analyser.message.MessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * netty根解码器
 * 依赖: NettyMessageAnalyserManager实例
 */
public class NettyTcpDecoder extends ByteToMessageDecoder {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(NettyTcpDecoder.class);

    private MessageAnalyser<ByteBuf> currentMessageAnalyser;
    private int currentReaderIndex = -1;

    private NettyMessageAnalyserManager nettyMessageAnalyserManager;

    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        if (in.isReadable()) {
            ByteBuf copied = in.copy();
            byte[] content = new byte[copied.readableBytes()];
            copied.readBytes(content);
            String hex = DatatypeConverter.printHexBinary(content);
            logger.info("hex: \r\n" + hex);
            logger.info("string: \r\n" + new String(content));
            logger.info("bytes: \r\n" + Arrays.toString(content));
            markReaderIndex(in);
            if (currentMessageAnalyser == null) {
                MessageAnalyser<ByteBuf> messageAnalyser = nettyMessageAnalyserManager.selectMessageAnalyser(in);
                //未找到消息解析器
                if (messageAnalyser == null) {
                    close(channelHandlerContext, "no MessageAnalyser found");
                    return;
                }
                currentMessageAnalyser = messageAnalyser;
            }
            Object message = currentMessageAnalyser.analyse(in);
            if (message != null) {
                logger.info(message.toString());
                out.add(message);
                //读取完一条消息后reset当前handler
                reset();
            } else {
                //reset reader index
                resetReaderIndex(in);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("app analyse message from: " + ctx.channel().localAddress() + "exception, connection is closing!", cause);
    }

    private void markReaderIndex(ByteBuf in) {
        currentReaderIndex = in.readerIndex();
    }

    private void resetReaderIndex(ByteBuf in) {
        in.readerIndex(currentReaderIndex);
        currentReaderIndex = -1;
    }


    private void reset() {
        currentMessageAnalyser = null;
    }

    private void close(ChannelHandlerContext channelHandlerContext, String message) {
        logger.error(message);
        channelHandlerContext.close();
    }

    public NettyTcpDecoder(NettyMessageAnalyserManager nettyMessageAnalyserManager) {
        this.nettyMessageAnalyserManager = nettyMessageAnalyserManager;
    }
}

package com.jy.im.base.component.decoder.tcp.netty;

import com.jy.im.base.component.analyser.message.MessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.exception.NoMessageAnalyserFoundException;
import com.jy.im.base.component.exception.NoMessageTranslatorFoundException;
import com.jy.im.base.component.exception.UnsupportedMessageTypeException;
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

    private int length = -1;
    private int currentReaderIndex = -1;
    private MessageAnalyser<ByteBuf> currentMessageAnalyser;
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
            //ensure bytes enough
            if(length == -1) {
                length = in.readShort();
                if(in.readableBytes() < length) {
                    return;
                }
            }
            markReaderIndex(in);
            if (currentMessageAnalyser == null) {
                MessageAnalyser<ByteBuf> messageAnalyser = nettyMessageAnalyserManager.selectMessageAnalyser(in);
                //未找到消息解析器
                if (messageAnalyser == null) {
                    throw new NoMessageAnalyserFoundException("no MessageAnalyser found");
                }
                currentMessageAnalyser = messageAnalyser;
            }
            try {
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
            } catch (UnsupportedMessageTypeException | NoMessageTranslatorFoundException e) {
                logger.error("", e);
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
        length = -1;
    }

    public NettyTcpDecoder(NettyMessageAnalyserManager nettyMessageAnalyserManager) {
        this.nettyMessageAnalyserManager = nettyMessageAnalyserManager;
    }
}

package com.jy.im.server.udp.listener.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DatagramPacketDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class NettyUdpDecoder extends DatagramPacketDecoder {

    private static final Logger logger = LoggerFactory.getLogger(NettyUdpDecoder.class);

    public NettyUdpDecoder(MessageToMessageDecoder<ByteBuf> decoder) {
        super(decoder);
    }
}



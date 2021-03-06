package com.jy.im.server.udp.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DatagramPacketDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyUdpDecoder extends DatagramPacketDecoder {

    private static final Logger logger = LoggerFactory.getLogger(NettyUdpDecoder.class);

    public NettyUdpDecoder(MessageToMessageDecoder<ByteBuf> decoder) {
        super(decoder);
    }
}



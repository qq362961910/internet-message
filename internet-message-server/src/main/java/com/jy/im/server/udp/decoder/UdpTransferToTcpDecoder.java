package com.jy.im.server.udp.decoder;

import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class UdpTransferToTcpDecoder extends MessageToMessageDecoder<ByteBuf> {

    private NettyTcpDecoder nettyTcpDecoder;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List out) throws Exception {
        nettyTcpDecoder.decode(ctx, buf, out);
    }

    public UdpTransferToTcpDecoder(NettyTcpDecoder nettyTcpDecoder) {
        this.nettyTcpDecoder = nettyTcpDecoder;
    }
}

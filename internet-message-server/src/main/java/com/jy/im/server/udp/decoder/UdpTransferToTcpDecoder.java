package com.jy.im.server.udp.decoder;

import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope("prototype")
@Component
public class UdpTransferToTcpDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Autowired
    private NettyTcpDecoder nettyTcpDecoder;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List out) throws Exception {
        nettyTcpDecoder.decode(ctx, buf, out);
    }

    public NettyTcpDecoder getNettyTcpDecoder() {
        return nettyTcpDecoder;
    }

    public UdpTransferToTcpDecoder setNettyTcpDecoder(NettyTcpDecoder nettyTcpDecoder) {
        this.nettyTcpDecoder = nettyTcpDecoder;
        return this;
    }
}

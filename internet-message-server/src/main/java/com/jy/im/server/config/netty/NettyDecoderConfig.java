package com.jy.im.server.config.netty;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.server.udp.decoder.NettyUdpDecoder;
import com.jy.im.server.udp.decoder.UdpTransferToTcpDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NettyDecoderConfig {

    /**
     * netty tcp decoder
     */
    @Scope("prototype")
    @Bean
    public NettyTcpDecoder nettyTcpDecoder(NettyMessageAnalyserManager nettyMessageAnalyserManager) {
        return new NettyTcpDecoder(nettyMessageAnalyserManager);
    }


    @Scope("prototype")
    @Bean
    public UdpTransferToTcpDecoder udpTransferToTcpDecoder(NettyTcpDecoder nettyTcpDecoder) {
        return new UdpTransferToTcpDecoder(nettyTcpDecoder);
    }


    @Scope("prototype")
    @Bean
    public NettyUdpDecoder nettyUdpDecoder(MessageToMessageDecoder<ByteBuf> decoder) {
        return new NettyUdpDecoder(decoder);
    }
}

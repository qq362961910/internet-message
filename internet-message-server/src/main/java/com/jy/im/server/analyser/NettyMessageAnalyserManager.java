package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyserManager;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class NettyMessageAnalyserManager extends AbstractMessageAnalyserManager<ByteBuf, Object> {

}

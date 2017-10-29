package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyser;
import io.netty.buffer.ByteBuf;

public abstract class NettyMessageAnalyser<Out> extends AbstractMessageAnalyser<ByteBuf, Out> {

}

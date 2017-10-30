package com.jy.im.base.component.analyser.protocol;

import com.jy.im.common.constants.MessageProtocol;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class AbstractMessageProtocolAnalyser<In> implements ProtocolAnalyser<In> {

    private final TypeParameterMatcher matcher;

    @Override
    public boolean support(In in) {
        boolean match = matcher.match(in);
        if (match) {
            MessageProtocol messageProtocol = analyser(in);
            return getSupportMessageProtocol() == messageProtocol;
        }
        return false;
    }

    /**
     * 获取支持的协议类型
     */
    public abstract MessageProtocol getSupportMessageProtocol();

    public AbstractMessageProtocolAnalyser() {
        matcher = TypeParameterMatcher.find(this, AbstractMessageProtocolAnalyser.class, "In");
    }
}

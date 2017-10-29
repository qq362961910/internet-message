package com.jy.im.base.component.analyser.protocol;

import com.jy.im.base.component.enums.MessageProtocol;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class AbstractMessageProtocolAnalyser<In> implements ProtocolAnalyser<In> {

    private final TypeParameterMatcher matcher;

    @Override
    public boolean support(Object in) {
        boolean match = matcher.match(in);
        if (match) {
            MessageProtocol messageProtocol = analyser((In) in);
            return getSupportMessageProtocol() == messageProtocol;
        }
        return false;
    }

    public AbstractMessageProtocolAnalyser() {
        matcher = TypeParameterMatcher.find(this, AbstractMessageProtocolAnalyser.class, "In");
    }
}

package com.jy.im.base.component.analyser.protocol;

import io.netty.util.internal.TypeParameterMatcher;

public abstract class AbstractMessageProtocolAnalyser<In> implements ProtocolAnalyser<In> {

    private final TypeParameterMatcher matcher;

    @Override
    public boolean support(Object in) {
        return matcher.match(in);
    }

    public AbstractMessageProtocolAnalyser() {
        matcher = TypeParameterMatcher.find(this, AbstractMessageProtocolAnalyser.class, "In");
    }
}

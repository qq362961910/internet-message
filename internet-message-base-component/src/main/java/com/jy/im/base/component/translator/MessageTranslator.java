package com.jy.im.base.component.translator;

import com.jy.im.common.constants.MessageType;

public interface MessageTranslator<In> {

    Object translate(In in);

    boolean support(MessageType type);
}

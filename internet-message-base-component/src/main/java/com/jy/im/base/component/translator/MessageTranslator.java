package com.jy.im.base.component.translator;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageContentType;

public interface MessageTranslator<In> {

    Object translate(In in);

    boolean support(MessageContentType type);

    MessageProtocol supportMessageProtocol();
}

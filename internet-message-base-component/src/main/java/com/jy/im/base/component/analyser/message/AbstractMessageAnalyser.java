package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象消息解析器
 */
public abstract class AbstractMessageAnalyser<In> implements MessageAnalyser<In> {

    /**
     * 消息转换器集合
     * */
    protected List<MessageTranslator<In>> messageTranslators = new ArrayList<>();

    /**
     * 1.消息协议解析器判断是否支持入参
     * 2.消息解析器判断是否支持该协议
     */
    @Override
    public boolean support(In in) {
        if(getProtocolAnalyser().support(in)) {
            MessageProtocol messageProtocol = getProtocolAnalyser().analyser(in);
            return messageProtocol == supportMessageProtocol();
        }
        return false;
    }

    /**
     * add message translator
     * */
    public void addMessageTranslator(MessageTranslator<In> messageTranslator) {
        if(messageTranslator != null) {
            this.messageTranslators.add(messageTranslator);
        }
    }

    public MessageTranslator<In> getMessageTranslator(MessageProtocol messageProtocol, MessageType messageType) {
        for(MessageTranslator<In> messageTranslator: messageTranslators) {
            if(messageTranslator.supportMessageProtocol() == messageProtocol && messageTranslator.support(messageType)) {
                return messageTranslator;
            }
        }
        return null;
    }

    /**
     * 一个消息解析器捆绑一个协议解析器(限定协议解析器和消息解析器入参类型一致)
     * 1.限定入参类型
     * 2.限定协议类型
     */
    public abstract ProtocolAnalyser<In> getProtocolAnalyser();

    /**
     * 支持的消息协议
     * */
    public abstract MessageProtocol supportMessageProtocol();

}

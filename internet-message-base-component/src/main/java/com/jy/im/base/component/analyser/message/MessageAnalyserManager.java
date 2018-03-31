package com.jy.im.base.component.analyser.message;


/**
 * 消息解析器管理器
 * 由于一个端口只能支持一个传输协议,如tcp or udp,所以入參类型一定一致
 */
public interface MessageAnalyserManager<In> {

    /**
     * 选取消息解析器
     */
    MessageAnalyser selectMessageAnalyser(In in);

    /**
     * 添加消息解析器
     */
    void addMessageAnalyser(MessageAnalyser<In> messageAnalyser);
}

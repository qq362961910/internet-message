package com.jy.im.base.component.analyser.message;


/**
 * 消息解析器管理器
 */
public interface MessageAnalyserManager {

    /**
     * 选取消息解析器
     */
    MessageAnalyser selectMessageAnalyser(Object in);
}

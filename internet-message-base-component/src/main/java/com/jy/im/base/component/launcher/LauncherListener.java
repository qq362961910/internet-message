package com.jy.im.base.component.launcher;


public interface LauncherListener {

    /**
     * 启动器启动完成
     * */
    void afterStartup(AbstractLauncher launcher);

    /**
     * 启动器关闭完成
     * */
    void afterClose(AbstractLauncher launcher);
}

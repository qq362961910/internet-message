package com.jy.im.base.component.launcher;


public interface LauncherListener {

    void afterStartup(AbstractLauncher launcher);

    void afterClose(AbstractLauncher launcher);
}

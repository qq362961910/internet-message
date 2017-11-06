package com.jy.im.base.component.launcher;


public interface LauncherListener {

    void startup(AbstractLauncher launcher);

    void close(AbstractLauncher launcher);
}

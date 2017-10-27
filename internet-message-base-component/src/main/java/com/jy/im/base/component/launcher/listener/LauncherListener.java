package com.jy.im.base.component.launcher.listener;


import com.jy.im.base.component.launcher.AbstractLauncher;

public interface LauncherListener {

    void startup(AbstractLauncher launcher);

    void close(AbstractLauncher launcher);
}

package com.jy.im.server.listener;

import com.jy.im.base.component.launcher.AbstractLauncher;
import com.jy.im.base.component.launcher.listener.LauncherListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultLauncherListener implements LauncherListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLauncherListener.class);

    @Override
    public void startup(AbstractLauncher launcher) {
        logger.info(launcher.getClass() + " start....");
    }

    @Override
    public void close(AbstractLauncher launcher) {
        logger.info(launcher.getClass() + " stop....");
    }
}

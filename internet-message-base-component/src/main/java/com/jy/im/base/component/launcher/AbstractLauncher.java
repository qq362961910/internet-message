package com.jy.im.base.component.launcher;


import com.jy.im.base.component.daemon.Daemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractLauncher implements Launcher {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLauncher.class);

    /**
     * 启动器状态
     */
    protected volatile boolean stop = false;
    /**
     * 服务器实例
     */
    protected final List<Daemon> daemonList = new ArrayList<>();
    /**
     * 启动实例
     * */
    protected final Set<Daemon> startedDaemonSet = Collections.synchronizedSet(new HashSet<>());
    /**
     * 停机实例
     */
    protected final List<Daemon> downDaemonList = Collections.synchronizedList(new ArrayList<>());
    /**
     * 启动器监听器
     */
    private final List<LauncherListener> launcherListenerList = new ArrayList<>();

    /**
     * 线程池
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 启动
     */
    @Override
    public void startup() {
        logger.info("launcher begin to startup");
        doStart();
        //回调监听器
        if (!launcherListenerList.isEmpty()) {
            logger.info("launcher listener list size: {}, begin to call LauncherListener#afterStartup(AbstractLauncher launcher)", launcherListenerList.size());
            launcherListenerList.forEach(listener -> listener.afterStartup(AbstractLauncher.this));
        }
        logger.info("launcher startup successfully");
    }

    public abstract void doStart();

    /**
     * 关闭
     */
    @Override
    public void close() {
        logger.info("launcher begin to shutdown");
        stop = true;
        doClose();
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Executor Service shutdown error", e);
            executorService.shutdownNow();
        }
        //回调监听器
        if (!launcherListenerList.isEmpty()) {
            logger.info("launcher listener list size: {}, begin to call LauncherListener#afterClose(AbstractLauncher launcher)", launcherListenerList.size());
            launcherListenerList.forEach(listener -> listener.afterClose(AbstractLauncher.this));
        }
        logger.info("launcher shutdown successfully");
    }

    protected void startServer(Daemon server) {
        if (!executorService.isShutdown()) {
            executorService.submit(() -> {
                server.beforeStart();
                server.start(AbstractLauncher.this);
            });
        }
    }

    protected void shutdownServer(Daemon server) {
        if (!executorService.isShutdown()) {
            executorService.submit(() -> {
                server.beforeShutdown();
                server.shutdown(AbstractLauncher.this);
            });
        }
    }

    public abstract void doClose();

    @Override
    public void daemonStartSuccess(Daemon daemon) {
        startedDaemonSet.add(daemon);
        logger.info("new daemon started, alive count: {}", startedDaemonSet.size());
        //因为server会阻塞线程,所以在这里进行调用afterStart()
        daemon.afterStart();
    }

    @Override
    public void daemonShutdownSuccess(Daemon daemon) {
        startedDaemonSet.remove(daemon);
        logger.info("daemon stopped, alive count: {}", startedDaemonSet.size());
        downDaemonList.add(daemon);
        daemon.afterShutdown();
    }

    public AbstractLauncher addDaemonList(List<Daemon> daemonList) {
        if (daemonList != null && !daemonList.isEmpty()) {
            this.daemonList.addAll(daemonList);
        }
        return this;
    }

    public AbstractLauncher addDaemon(Daemon daemon) {
        if (daemon != null) {
            daemonList.add(daemon);
        }
        return this;
    }

    public List<LauncherListener> getLauncherListenerList() {
        return launcherListenerList;
    }

    public AbstractLauncher setLauncherListenerList(List<LauncherListener> launcherListenerList) {
        if (launcherListenerList != null && !launcherListenerList.isEmpty()) {
            this.launcherListenerList.addAll(launcherListenerList);
        }
        return this;
    }
    public boolean getLauncherStatus() {
        return !stop;
    }
}

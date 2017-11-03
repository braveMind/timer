package com.jun.timer.client;

/**
 * Created by jun
 * 17/7/23 上午9:08.
 * des:
 */
public class ClientConfig {

    private int eventThread=Runtime.getRuntime().availableProcessors();
    private int connectTimeout = 2000;

    public ClientConfig() {
    }

    public ClientConfig(int eventThread, int connectTimeout) {
        this.eventThread = eventThread;
        this.connectTimeout = connectTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getEventThread() {
        return eventThread;
    }

    public void setEventThread(int eventThread) {
        this.eventThread = eventThread;
    }

}

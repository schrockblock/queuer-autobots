package com.autobots.queuer.interfaces;

/**
 * Created by mammothbane on 1/8/14.
 */
public interface LoginManagerCallback {
    public void startConnection();
    public void finishedConnection(boolean success);
}

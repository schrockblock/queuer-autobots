package com.autobots.queuer.Interfaces;

/**
 * Created by mammothbane on 1/8/14.
 */
public interface LoginManagerCallback {
    public void startConnection();
    public void finishedConnection(boolean success);
}


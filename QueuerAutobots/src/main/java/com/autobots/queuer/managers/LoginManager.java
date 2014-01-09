package com.autobots.queuer.managers;

import com.autobots.queuer.interfaces.LoginManagerCallback;

/**
 * Created by mammothbane on 1/8/14.
 */
public class LoginManager {

    private LoginManagerCallback callback;

    public void setCallback(LoginManagerCallback callback) {
        this.callback = callback;
    }

    public void login(String username, String password) throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.startConnection();
        authenticate(username, password);

    }

    public void authenticate (String username, String password) {

    }

    public void authSuccess() throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.finishedConnection(true);
    }

    public void authFailure() throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.finishedConnection(false);
    }


}

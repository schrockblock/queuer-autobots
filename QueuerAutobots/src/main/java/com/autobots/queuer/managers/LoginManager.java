package com.autobots.queuer.managers;

import android.content.Context;
import com.autobots.queuer.interfaces.AuthenticatedCallback;

/**
 * Created by mammothbane on 1/8/14.
 */
public class LoginManager {

    private static LoginManager ref = new LoginManager();
    private static boolean loggedIn = false;
    private ManagerKernel kernel;


    private LoginManager() {
        kernel = new ManagerKernel(false);
    }

    public static LoginManager getLoginManager() {
        return ref;
    }

    public void setCallback(Context context, AuthenticatedCallback callback) {
        kernel.setCallback(context, callback);
    }

    public void login (String username, String password) {
        try {
            kernel.crLogin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean login) {
        loggedIn = login;
    }

}

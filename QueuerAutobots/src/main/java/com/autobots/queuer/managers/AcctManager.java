package com.autobots.queuer.managers;

import android.content.Context;
import com.autobots.queuer.interfaces.AuthenticatedCallback;

/**
 * Created by mammothbane on 1/16/14.
 */
public class AcctManager {
    private static AcctManager ref = new AcctManager();
    private ManagerKernel kernel;


    private AcctManager(){
        kernel = new ManagerKernel(true);
    }

    public static AcctManager getAcctManager() {return ref;}

    public void setCallback(Context context, AuthenticatedCallback callback) {
        kernel.setCallback(context, callback);

    }

    public void create (String username, String password) {
        try {
            kernel.crLogin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

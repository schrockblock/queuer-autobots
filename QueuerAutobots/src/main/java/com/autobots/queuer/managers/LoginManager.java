package com.autobots.queuer.managers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.autobots.queuer.Constants;
import com.autobots.queuer.QueuerApplication;
import com.autobots.queuer.interfaces.AuthenticatedCallback;
import com.autobots.queuer.models.SignInModel;
import com.google.gson.Gson;

import com.autobots.queuer.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mammothbane on 1/8/14.
 */
public class LoginManager {

    private static LoginManager ref = new LoginManager();
    private AuthenticatedCallback callback;
    private Context context;

    private LoginManager() {

    }

    public static LoginManager getLoginManager() {
        return ref;
    }

    public void setCallback(Context context, AuthenticatedCallback callback) {
        this.context = context;
        this.callback = callback;

    }



    public void login(String username, String password) throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.startConnection();
        authenticate(username, password);
    }

    private void authenticate (String username, String password) throws Exception {
        authSuccess();
        /*SignInModel model = new SignInModel(username, password);
        JSONObject signInJson = null;
        try {
            signInJson = new JSONObject(new Gson().toJson(model));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_VOLLEY_SESSION, signInJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    authSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    authFailure();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);
        */

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

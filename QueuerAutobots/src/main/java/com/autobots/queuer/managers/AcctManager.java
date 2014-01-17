package com.autobots.queuer.managers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.autobots.queuer.Constants;
import com.autobots.queuer.QueuerApplication;
import com.autobots.queuer.interfaces.AuthenticatedCallback;
import com.autobots.queuer.interfaces.UserManager;
import com.autobots.queuer.models.SignInModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import com.autobots.queuer.managers.ManagerKernel;

/**
 * Created by mammothbane on 1/16/14.
 */
public class AcctManager implements UserManager {
    private static AcctManager ref = new AcctManager();
    //protected AuthenticatedCallback callback;
    //protected Context context;
    private ManagerKernel kernel;


    private AcctManager(){
        kernel = new ManagerKernel(this, true);
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



    /*public void setCallback(Context context, AuthenticatedCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void create (String username, String password) throws Exception {
        if (callback == null) throw new Exception("must supply callback");
        callback.startConnection();
        createAccount(username, password);
    }

    private void createAccount (String username, String password) throws Exception {
        SignInModel model = new SignInModel(username, password);
        JSONObject signInJson = null;
        try {
            signInJson = new JSONObject(new Gson().toJson(model));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_VOLLEY_ACCT, signInJson, new Response.Listener<JSONObject>() {
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
                int response = 0;
                try {
                    response = Integer.parseInt(volleyError.networkResponse.headers.get("Status"));
                    String responseMsg = "";
                    switch (response) {
                        case 200:
                            responseMsg = "weird. that's not an error.";
                            break;
                        case 400:
                            responseMsg = "that's a problem with our code " +
                                    "let us know";
                            break;
                        case 401:
                            responseMsg = "your account name may already exist";
                            break;
                        case 403:
                            responseMsg = "you don't have permission to access our servers";
                            break;
                        case 404:
                            responseMsg = "huh. we can't seem to find our password server.";
                            break;
                        default:
                            responseMsg = "Server responded with unhandled error " + response;
                            break;
                    }
                    Toast.makeText(AcctManager.this.context, responseMsg, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AcctManager.this.context, "The server can't be reached.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                try {
                    authFailure();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);


    }

    public void authSuccess() throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.finishedConnection(true);
    }

    public void authFailure() throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.finishedConnection(false);
    }
    */
}

package com.autobots.queuer.managers;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.autobots.queuer.Constants;
import com.autobots.queuer.QueuerApplication;
import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.AuthenticatedCallback;
import com.autobots.queuer.models.SignInModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mammothbane on 1/17/14.
 */
public class ManagerKernel {

    private AuthenticatedCallback callback;
    private Context context;
    private boolean create;
    private boolean debug;

    public ManagerKernel(boolean create) {
        this.create = create;
    }

    public ManagerKernel(boolean create, boolean debug) {
        this.create = create;
        this.debug = debug;
    }

    protected void setCallback(Context context, AuthenticatedCallback callback) {
        this.context = context;
        this.callback = callback;

    }

    protected void accAuth (String username, String password) throws Exception {
       if (context == null) throw new Exception("you must supply a context/use setcallback");
       if (debug) {
           authResult(true, callback);
           return;
       }
       String URL;
       if (create) { URL = Constants.URL_VOLLEY_ACCT; }
       else { URL = Constants.URL_VOLLEY_SESSION; }

       SignInModel model = new SignInModel(username, password);
       JSONObject signInJson = null;
       try {
           signInJson = new JSONObject(new Gson().toJson(model));
       } catch (JSONException e) {
           e.printStackTrace();
       }
       JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
               URL, signInJson, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject jsonObject) {
               try {
                   authResult(true, callback);
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
                           if (create) {responseMsg = "your account name may already exist";}
                           else {responseMsg = "your username or password is invalid";}
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
                   Toast.makeText(context, responseMsg, Toast.LENGTH_SHORT).show();
               } catch (Exception e) {
                   Toast.makeText(context, "The server can't be reached.", Toast.LENGTH_SHORT).show();
                   e.printStackTrace();
               }
               try {
                   authResult(false, callback);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       });

       ((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);

   }

    protected void crLogin (String username, String password) throws Exception {
        if (this.callback == null) throw new Exception("you must supply a callback");
        callback.startConnection();
        try {
            accAuth(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void authResult(boolean success, AuthenticatedCallback callback) throws Exception {
        if (callback == null) throw new Exception("must supply LoginManagerCallback reference");
        callback.finishedConnection(success);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }


}
package com.autobots.queuer.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.AuthenticatedCallback;
import com.autobots.queuer.managers.LoginManager;

public class LoginActivity extends ActionBarActivity implements AuthenticatedCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login_title);
        Button login = (Button)findViewById(R.id.loginButton);
        Button crAccount = (Button)findViewById(R.id.acct_button);
        final EditText user = (EditText)findViewById(R.id.login_et).findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.login_et).findViewById(R.id.password);
        final LoginManager manager = LoginManager.getLoginManager();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    manager.login(user.getText().toString(), password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        crAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this, AccountActivity.class));
            }
        });
    }

    public void startConnection() {
        ((ProgressBar)findViewById(R.id.loginSpinner).findViewById(R.id.loginProgress)).setVisibility(View.VISIBLE);
        TextView connectionText = (TextView)findViewById(R.id.loginSpinner).findViewById(R.id.progress_tv);
        connectionText.setText("Logging in...");
        connectionText.setVisibility(View.VISIBLE);
    }

    public void finishedConnection(boolean success) {
        ((ProgressBar)findViewById(R.id.loginSpinner).findViewById(R.id.loginProgress)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.loginSpinner).findViewById(R.id.progress_tv)).setVisibility(View.INVISIBLE);
        if (success) {
            Toast.makeText(this, "Login succeeded.", Toast.LENGTH_SHORT).show();
            LoginManager.setLoggedIn(true);
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //case R.id.action_settings:
            //    return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

}

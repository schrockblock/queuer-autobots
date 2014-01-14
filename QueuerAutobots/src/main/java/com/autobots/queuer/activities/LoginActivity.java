package com.autobots.queuer.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.LoginManagerCallback;
import com.autobots.queuer.managers.LoginManager;

public class LoginActivity extends ActionBarActivity implements LoginManagerCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button)findViewById(R.id.loginButton);
        Button crAccount = (Button)findViewById(R.id.create_account);
        final EditText user = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        final ProgressBar spin = (ProgressBar)findViewById(R.id.loginProgress);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager manager = LoginManager.getLoginManager();
                manager.setCallback(LoginActivity.this);
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
                setContentView(R.layout.activity_account);
            }
        });

    }

    public void startConnection() {
        final ProgressBar spin = (ProgressBar)findViewById(R.id.loginProgress);
        spin.setVisibility(View.VISIBLE);
    }

    public void finishedConnection(boolean success) {
        final ProgressBar spin = (ProgressBar)findViewById(R.id.loginProgress);
        spin.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

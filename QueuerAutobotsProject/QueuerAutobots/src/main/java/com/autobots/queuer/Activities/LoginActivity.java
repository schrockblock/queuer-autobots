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
import com.autobots.queuer.Interfaces.LoginManagerCallback;
import com.autobots.queuer.Managers.LoginManager;

public class LoginActivity extends ActionBarActivity implements LoginManagerCallback {

    protected final ProgressBar spin = (ProgressBar)findViewById(R.id.loginProgress);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button)findViewById(R.id.loginButton);
        final EditText user = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager manager = new LoginManager();
                try {
                    manager.login(user.getText().toString(), password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void startConnection() {
        spin.setVisibility(View.VISIBLE);
    }

    public void finishedConnection(boolean success) {
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
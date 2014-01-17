package com.autobots.queuer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.LoginManagerCallback;

/**
 * Created by mammothbane on 1/13/14.
 */
public class AccountActivity extends Activity implements LoginManagerCallback {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        final Button create = (Button)findViewById(R.id.acct_create);
        final Button cancel = (Button)findViewById(R.id.acct_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void startConnection() {

    }

    public void finishedConnection(boolean success) {

    }
}
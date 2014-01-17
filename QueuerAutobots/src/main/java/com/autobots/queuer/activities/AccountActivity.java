package com.autobots.queuer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.autobots.queuer.QueuerApplication;
import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.AuthenticatedCallback;
import com.autobots.queuer.managers.AcctManager;

/**
 * Created by mammothbane on 1/13/14.
 */
public class AccountActivity extends Activity implements AuthenticatedCallback {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final EditText username = (EditText)findViewById(R.id.acct_et).findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.acct_et).findViewById(R.id.password);
        final Button create = (Button)findViewById(R.id.acct_create);
        final Button cancel = (Button)findViewById(R.id.acct_cancel);
        final AcctManager manager = AcctManager.getAcctManager();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.setCallback(AccountActivity.this, AccountActivity.this);
                try {
                    manager.create(username.getText().toString(), password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void startConnection() {
        ((ProgressBar)findViewById(R.id.acct_spinner).findViewById(R.id.loginProgress)).setVisibility(View.VISIBLE);

    }

    public void finishedConnection(boolean success) {
        ((ProgressBar)findViewById(R.id.acct_spinner).findViewById(R.id.loginProgress)).setVisibility(View.INVISIBLE);
        String acct_notice = "Your account creation ";
        if (success) {
            acct_notice += "succeeded!";
        } else {
            acct_notice += "failed.";
        }
        Toast acct_worked = Toast.makeText(this, acct_notice, Toast.LENGTH_SHORT);
        acct_worked.show();
        if (success) finish();
    }
}
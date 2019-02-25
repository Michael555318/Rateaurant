package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText username;
    private EditText editPassword;
    private String password;
    private TextView createAccount;

    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        // Initialize Backendless connection
        Backendless.initApp(this, Credentials.APP_ID, Credentials.API_KEY);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Your username pls", Toast.LENGTH_SHORT).show();
                } else {
                    loginToBackendless();
                }

            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                infoIntent.putExtra(EXTRA_MESSAGE, username.getText().toString());
                startActivity(infoIntent);
            }
        });

        Intent accountIntent = getIntent();
        username.setText(accountIntent.getStringExtra(LoginActivity.EXTRA_MESSAGE));
    }

    private void loginToBackendless() {
        String login = username.getText().toString();
        String passward = editPassword.getText().toString();
        Backendless.UserService.login(login, passward, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                //Start a new activity.  This method is called when login is complete and successful
                Toast.makeText(LoginActivity.this, response.getEmail() + " Logged In!", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(LoginActivity.this, RestaurantsActivity.class);
                startActivity(login);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        buttonLogin = findViewById(R.id.button_login_login);
        username = findViewById(R.id.editText_login_username);
        editPassword = findViewById(R.id.editText_login_password);
        createAccount = findViewById(R.id.textView_login_createaccount);
    }
}

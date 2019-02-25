package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText name;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private Button submit;
    public static String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireWidgets();

        submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString()) && name.getText().length() != 0) {
                        final BackendlessUser user = new BackendlessUser();
                        user.setProperty( "email", email.getText().toString());
                        user.setPassword( confirmPassword.getText().toString() );
                        user.setProperty("username", username.getText().toString());
                        user.setProperty("name", name.getText().toString());
                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Toast.makeText(CreateAccountActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                Intent accountIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                accountIntent.putExtra(EXTRA_MESSAGE, username.getText().toString());
                                startActivity(accountIntent);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(CreateAccountActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(CreateAccountActivity.this, "Incorrect or incomplete information", Toast.LENGTH_SHORT).show();
                    }
                }
        });

    }

    private void wireWidgets() {
        name = findViewById(R.id.editText_createaccount_name);
        username = findViewById(R.id.editText_createaccount_username);
        password = findViewById(R.id.editText_createaccount_password);
        confirmPassword = findViewById(R.id.editText_createaccount_comfirmpassword);
        email = findViewById(R.id.editText_createaccount_email);
        submit = findViewById(R.id.button_createaccount_submit);
    }
}

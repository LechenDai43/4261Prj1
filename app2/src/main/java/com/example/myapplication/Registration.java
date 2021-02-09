package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    EditText usernameTextFieldRg, emailTextFieldRg, passwordTextFieldRg, confirmTextFieldRg;
    Button registerButtonRg, returnButtonRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerButtonRg = (Button) findViewById(R.id.registerButtonRg);
        registerButtonRg.setOnClickListener(new registerListener());
        returnButtonRg = (Button) findViewById(R.id.returnButtonRg);
        returnButtonRg.setOnClickListener(new returnListener());
    }

    class registerListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            usernameTextFieldRg = (EditText) findViewById(R.id.usernameTextFieldRg);
            emailTextFieldRg = (EditText) findViewById(R.id.emailTextFieldRg);
            passwordTextFieldRg = (EditText) findViewById(R.id.passwordTextFieldRg);
            confirmTextFieldRg = (EditText) findViewById(R.id.confirmTextFieldRg);

            TextView errorMessage = (TextView) findViewById(R.id.errorMessage);

            String username = usernameTextFieldRg.getText().toString();
            String email = emailTextFieldRg.getText().toString();
            String password = passwordTextFieldRg.getText().toString();
            String confirmPwd = confirmTextFieldRg.getText().toString();

            if (username == null || username.length() < 1 || username.equals("E-mail")) {
                errorMessage.setText("The username is invalid.");
            } else if (email == null || email.length() < 5) {
                errorMessage.setText("The email address is invalid.");
            } else if (password == null || password.length() < 6 || password.equals("Password")) {
                errorMessage.setText("Please enter a valid password.");
            } else if (!confirmPwd.equals(password)) {
                errorMessage.setText("Please enter same password.");
            } else {
                boolean usernameError = false;
                boolean emailError = false;
                boolean passwordError = false;
                boolean confirmError = false;
                boolean noErrors = true;
                    /*
                    Todo...
                        1. Check if the username is valid
                        2. Check if the password is valid
                     */
                if (usernameError) {
                    errorMessage.setText("The username does not exist.");
                } else if (emailError) {
                    errorMessage.setText("The email does not exist.");
                } else if (passwordError) {
                    errorMessage.setText("The password is not correct.");
                } else if (confirmError) {
                    errorMessage.setText("The re-entered password is not correct");
                } else if (noErrors) {
                    errorMessage.setText("");
                    Intent nextPage = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(nextPage);
                }
            }
        }
    }
    class returnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(nextPage);
        }
    }
}
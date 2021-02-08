package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView errorMessage= (TextView)findViewById(R.id.errorMessage);
        errorMessage.setText("");

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new LoginListener());

        Button registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new RegisterListenser());

        Button forgetButton = (Button)findViewById(R.id.forgetButton);
        forgetButton.setOnClickListener(new FogetListenser());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText usernameTextField = (EditText)findViewById(R.id.usernameTextField);
            EditText passwordTextField = (EditText)findViewById(R.id.passwordTextField);

            TextView errorMessage = (TextView)findViewById(R.id.errorMessage);

            String username = usernameTextField.getText().toString();
            String password = passwordTextField.getText().toString();

            if (username == null || username.length() < 1 || username.equals("E-mail")) {
                errorMessage.setText("The username is invalid.");
            }
            else if (password == null || password.length() < 6 || password.equals("Password")) {
                errorMessage.setText("Please enter a valid password.");
            }
            else {
                boolean usernameError = false;
                boolean passwordError = false;
                boolean noErrors = true;
                /*
                Todo...
                    1. Check if the username is valid
                    2. Check if the password is valid
                 */
                if (usernameError) {
                    errorMessage.setText("The username does not exist.");
                }
                else if (passwordError) {
                    errorMessage.setText("The password is not correct.");
                }
                else if (noErrors) {
                    errorMessage.setText("");
                    Intent nextPage = new Intent(getApplicationContext(), Home.class);
                    startActivity(nextPage);
                }
            }
        }
    }

    class RegisterListenser implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), Registration.class);
            startActivity(nextPage);
        }
    }

    class FogetListenser implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), Forget.class);
            startActivity(nextPage);
        }
    }
}
package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
        registerButton.setOnClickListener(new RegisterListener());

        Button forgetButton = (Button)findViewById(R.id.forgetButton);
        forgetButton.setOnClickListener(new ForgetListener());
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

                CheckLogIn cli = new CheckLogIn();
                cli.passwordTextField = passwordTextField;
                cli.email = usernameTextField.getText().toString();
                cli.errorMessage = errorMessage;
                cli.context = getApplicationContext();
                cli.execute("");
            }
        }
    }

    class RegisterListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), Registration.class);
            startActivity(nextPage);
        }
    }

    class ForgetListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), Forget.class);
            startActivity(nextPage);
        }
    }

    class CheckLogIn extends AsyncTask<String, String, String> {
        EditText passwordTextField = null;
        String email = null;
        TextView errorMessage = null;
        boolean success = false, errorSet = false;
        Context context = null;

        @Override
        protected String doInBackground(String... strings) {
            if (passwordTextField != null && email != null && errorMessage != null) {
                try {
                    URL url = new URL(GlobalVariables.ADDRESS + "users.php");
                    HttpURLConnection httpURLConnection = null;
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("u1", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream =  httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String result = bufferedReader.readLine();
//                    while (result != null) {
//                        System.out.println(result);
//                        result = bufferedReader.readLine();
//                    }
                    if (result.equals("Account not find.")) {
                        success = false;
                        errorMessage.setText("This email was not found.");
                        errorSet = true;
                    }
                    else {
                        String[] parts = result.split(":");
                        int interations = Integer.parseInt(parts[0]);
                        byte[] salt = fromHex(parts[1]);
                        byte[] hash = fromHex(parts[2]);
                        PBEKeySpec spec = new PBEKeySpec(passwordTextField.getText().toString().toCharArray(),
                                salt, interations, hash.length * 8);
                        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        byte[] testHash = skf.generateSecret(spec).getEncoded();

                        int diff = hash.length ^ testHash.length;
                        for (int i = 0; i < hash.length && i < testHash.length; i++) {
                            diff |= hash[i] ^ testHash[i];
                        }

                        if (diff == 0) {
                            success = true;
                        }
                        else {
                            success = false;
                            errorMessage.setText("Password is incorrect.");
                            errorSet = true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        private byte[] fromHex(String hex) throws NoSuchAlgorithmException
        {
            byte[] bytes = new byte[hex.length() / 2];
            for(int i = 0; i<bytes.length ;i++)
            {
                bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
            }
            return bytes;
        }

        @Override
        protected void onPreExecute() {
            if (errorMessage != null) {
                errorMessage.setText("Checking credentials...");
            }
        }

        @Override
        protected void onPostExecute(String args) {
            if (success && context != null) {
                Intent nextPage = new Intent(context, Home.class);
                nextPage.putExtra("email", email);
                startActivity(nextPage);
            }
            else {
                if (passwordTextField != null) {
                    passwordTextField.setText("");
                }
                if (errorMessage != null && context == null) {
                    errorMessage.setText("Internal error...");
                    errorSet = true;
                }
                if (!errorSet) {
                    errorMessage.setText("Internal error...");
                    errorSet = true;
                }
            }
        }
    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
        public boolean isValidEmailAddress(String email) {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            return m.matches();
        }

        @Override
        public void onClick(View v) {
            usernameTextFieldRg = (EditText) findViewById(R.id.usernameTextFieldRg);
            emailTextFieldRg = (EditText) findViewById(R.id.emailTextFieldRg);
            passwordTextFieldRg = (EditText) findViewById(R.id.passwordTextFieldRg);
            confirmTextFieldRg = (EditText) findViewById(R.id.confirmTextFieldRg);

            TextView errorMessage = (TextView) findViewById(R.id.errorMessageRg);

            String username = usernameTextFieldRg.getText().toString();
            String email = emailTextFieldRg.getText().toString();
            String password = passwordTextFieldRg.getText().toString();
            String confirmPwd = confirmTextFieldRg.getText().toString();

            if (username == null || username.length() < 1 || username.equals("E-mail")) {
                errorMessage.setText("The username is invalid.");
            } else if (email == null || email.length() < 5 || ! isValidEmailAddress(email)) {
                errorMessage.setText("The email address is invalid.");
            } else if (password == null || password.length() < 6 || password.equals("Password")) {
                errorMessage.setText("Please enter a valid password.");
            } else if (!confirmPwd.equals(password)) {
                errorMessage.setText("Please enter same password.");
            } else {
                DoRegistration dr = new DoRegistration();
                dr.unhashed = password;
                dr.username = username;
                dr.email = email;
                dr.errorMessage = errorMessage;
                dr.context = getApplicationContext();
                dr.execute("");
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

    class DoRegistration extends AsyncTask<String, String, String> {
        TextView errorMessage = null;
        String email = null, username = null, unhashed = null;
        boolean success = false, setError = false;
        Context context = null;


        @Override
        protected String doInBackground(String... strings) {
            if (errorMessage != null && email != null && username != null && unhashed != null) {
                try {
                    URL url = new URL(GlobalVariables.ADDRESS + "users.php");
                    HttpURLConnection httpURLConnection = null;
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("u2", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream =  httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String result = bufferedReader.readLine();
                    if (result.equals("Email not used.")) {
                        CreateAccount ca = new CreateAccount();
                        ca.errorMessage = errorMessage;
                        ca.email = email;
                        ca.context = context;
                        ca.username = username;
                        ca.unhashed = unhashed;
                        ca.execute("");
                    }
                    else {
                        while (result != null) {
                            System.out.println(result);
                            result = bufferedReader.readLine();
                        }
                        success = false;
                        errorMessage.setText("This email is already used.");
                        setError = true;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (errorMessage != null) {
                errorMessage.setText("Checking duplication...");
            }
        }

        class CreateAccount extends AsyncTask<String, String, String> {
            TextView errorMessage = null;
            String email = null, username = null, unhashed = null;
            boolean success = false, setError = false;
            Context context = null;

            private byte[] getSalt() throws NoSuchAlgorithmException
            {
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                byte[] salt = new byte[16];
                sr.nextBytes(salt);
                return salt;
            }

            private String toHex(byte[] array) throws NoSuchAlgorithmException
            {
                BigInteger bi = new BigInteger(1, array);
                String hex = bi.toString(16);
                int paddingLength = (array.length * 2) - hex.length();
                if(paddingLength > 0)
                {
                    return String.format("%0"  +paddingLength + "d", 0) + hex;
                }else{
                    return hex;
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                if (errorMessage != null && email != null && username != null && unhashed != null) {
                    try {
                        String hashed = "";
                        int iterations = 1000;
                        char[] chars = unhashed.toCharArray();
                        byte[] salt = getSalt();

                        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
                        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        byte[] hash = skf.generateSecret(spec).getEncoded();
                        hashed =  iterations + ":" + toHex(salt) + ":" + toHex(hash);

                        URL url = new URL(GlobalVariables.ADDRESS + "users.php");
                        HttpURLConnection httpURLConnection = null;
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("u3", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" +
                                URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                                URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(hashed, "UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream =  httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        String result = bufferedReader.readLine();
                        if (result.equals("Success!")) {
                            success = true;

                        }
                        else {
                            while (result != null) {
                                System.out.println(result);
                                result = bufferedReader.readLine();
                            }
                            success = false;
                            errorMessage.setText("Internal error.");
                            setError = true;
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                if (errorMessage != null) {
                    errorMessage.setText("Creating account...");
                }
            }

            @Override
            protected void onPostExecute(String args) {
                if (success && context != null) {
                    Intent nextPage = new Intent(context, Home.class);
                    nextPage.putExtra("email", email);
                    errorMessage.setText("");
                    startActivity(nextPage);
                }
                else {
                    if (errorMessage != null && context == null) {
                        errorMessage.setText("Internal error...");
                        setError = true;
                    }
                    if (!setError && errorMessage != null) {
                        errorMessage.setText("Internal error...");
                        setError = true;
                    }
                }
            }
        }
    }

}
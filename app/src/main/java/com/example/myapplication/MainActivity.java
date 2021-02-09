package com.example.myapplication;

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.myapplication.DBStrings.DATABASE_NAME;
import static com.example.myapplication.DBStrings.DATABASE_URL;
import static com.example.myapplication.DBStrings.PASSWORD;
import static com.example.myapplication.DBStrings.USERNAME;

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
            GetData task = new GetData();
            task.execute("");
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

//                    errorMessage.setText("");
//                    Intent nextPage = new Intent(getApplicationContext(), Home.class);
//                    startActivity(nextPage);
                }
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

    public class GetData extends AsyncTask<String, String, String> {
        String msg = "";
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://" + DATABASE_URL + "/" + DATABASE_NAME;

        @Override
        protected String doInBackground(String... strings) {
//            Connection conn = null;
//            Statement stmt = null;
//            try {
//                Class.forName(JDBC_DRIVER);
//                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//
//                stmt = conn.createStatement();
//                String sql = "select username, email from users;";
//
//                ResultSet rs = stmt.executeQuery(sql);
//
//                while (rs.next()) {
//                    String username_ = rs.getString("username");
//                    String email_ = rs.getString("email");
//                    TextView errorMessage = (TextView)findViewById(R.id.errorMessage);
//                    errorMessage.setText(errorMessage.getText() + username_ + ": " + email_ + ";\r\n");
//                }
//
//            }
//            catch (SQLException connError) {
//                msg = "An exception was thrown for JDBC.";
//                connError.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            finally {
//                try {
//                    stmt.close();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//                try {
//                    conn.close();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            }

            try {
                URL url = new URL("http://128.61.77.135/creatures.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode("lalalalalalalla", "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream =  httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = bufferedReader.readLine();
                while (line  != null) {
                    TextView errorMessage = (TextView)findViewById(R.id.errorMessage);
                    errorMessage.setText(line + ";\r\n");
//
                    line = bufferedReader.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }
    }


}
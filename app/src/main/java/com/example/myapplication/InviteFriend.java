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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class InviteFriend extends AppCompatActivity {
    String username, email, othername, otheremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);

        username = getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");
        othername = getIntent().getExtras().getString("othername");
        otheremail = getIntent().getExtras().getString("otheremail");

        TextView nameTV = (TextView)findViewById(R.id.userNameTextViewIf);
        TextView mailTV = (TextView)findViewById(R.id.emailTextViewIf);
        nameTV.setText(othername);
        mailTV.setText(otheremail);

        Button invite = (Button)findViewById(R.id.inviteButtonIf);
        invite.setOnClickListener(new InviteListener());
        Button returnBtn = (Button)findViewById(R.id.returnButtonIf);
        returnBtn.setOnClickListener(new ReturnFromInvingListener());
    }

    class InviteListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText nickName = (EditText)findViewById(R.id.nickenameTextFieldIf);
            String comment = nickName.getText().toString();

            if (comment == null || comment.length() < 1) {
                comment = othername;
            }

            InviteConnector ic = new InviteConnector();
            ic.comment = comment;
            ic.context = getApplicationContext();
            ic.execute();
        }
    }

    class InviteConnector extends AsyncTask<String, String, String> {
        String comment;
        Context context;

        @Override
        protected String doInBackground(String... strings) {
            if (comment != null) {
                try {
                    // connect to backend
                    URL url = new URL(GlobalVariables.ADDRESS + "invitations.php");
                    HttpURLConnection httpURLConnection = null;
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    // pass data to the backend
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("i3", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("otheremail", "UTF-8") + "=" + URLEncoder.encode(otheremail, "UTF-8") + "&" +
                            URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(comment, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // get data from the backend
                    InputStream inputStream =  httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line = bufferedReader.readLine();
                    while (line  != null) {
                        System.out.println(line);
                        line = bufferedReader.readLine();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Intent nextPage = new Intent(getApplicationContext(), Home.class);
            nextPage.putExtra("username", username);
            nextPage.putExtra("email", email);
            startActivity(nextPage);
        }
    }

    class ReturnFromInvingListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), Home.class);
            nextPage.putExtra("username", username);
            nextPage.putExtra("email", email);
            startActivity(nextPage);
        }
    }
}
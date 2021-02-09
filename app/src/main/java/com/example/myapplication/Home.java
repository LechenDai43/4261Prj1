package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    String username = "", email = "";
    EditText searchBarTextFieldHm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");
        TextView header = (TextView) findViewById(R.id.headerTextViewHm);
        header.setText(username);

        searchBarTextFieldHm = (EditText) findViewById(R.id.searchBarTextFieldHm);
        ImageButton searchButtonHm = (ImageButton) findViewById(R.id.searchButtonHm);
        ImageButton notificationButtonHm = (ImageButton) findViewById(R.id.notificationButtonHm);
        Button logOutButtonHm = (Button) findViewById((R.id.logOutButtonHm));

        logOutButtonHm.setOnClickListener(new LogoutListener());
        searchButtonHm.setOnClickListener(new SearchFriendListener(this));
        notificationButtonHm.setOnClickListener(new OpenNotificationListener());

        RenderFriend rf = new RenderFriend();
        rf.searchBarTextFieldHm = searchBarTextFieldHm;
        rf.context = this;
        rf.myListView = (ListView)findViewById(R.id.listViewHm);
        rf.execute("");
    }

    class OpenNotificationListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }

    class LogoutListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(nextPage);
        }
    }

    class SearchFriendListener implements View.OnClickListener {
        Context c;

        public SearchFriendListener(Context con) {
            c = con;
        }

        @Override
        public void onClick(View view) {
            RenderFriend rf = new RenderFriend();
            rf.searchBarTextFieldHm = searchBarTextFieldHm;
            rf.context = c;
            rf.myListView = (ListView)findViewById(R.id.listViewHm);
            rf.execute("");
        }
    }

    class RenderFriend extends AsyncTask<String, String, String> {
        EditText searchBarTextFieldHm;
        Context context;
        ArrayList<String> fms = new ArrayList<>(), nns = new ArrayList<>(), rs = new ArrayList<>();
        ListView myListView;

        @Override
        protected String doInBackground(String... strings) {
            if (searchBarTextFieldHm != null) {
                String key = searchBarTextFieldHm.getText().toString();
                try {
                    URL url = new URL(GlobalVariables.ADDRESS + "friends.php");
                    HttpURLConnection httpURLConnection = null;
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = "";
                    if (key == null || key.length() < 1 || key.equals("Enter a friend name...")) {
                        post_data = URLEncoder.encode("f1", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" +
                                URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    }
                    else {
                        post_data = URLEncoder.encode("f2", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" +
                                URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                                URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");
                    }
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream =  httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 3) {
                            fms.add(parts[0]);
                            nns.add(parts[1]);
                            rs.add(parts[2]);
                        }
                        line = bufferedReader.readLine();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            if (context != null && myListView != null) {
                FriendAdapter friendAdapter = new FriendAdapter(context, nns, fms, rs);
                myListView.setAdapter(friendAdapter);

            }
        }
    }
}
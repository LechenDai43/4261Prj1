package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username = getIntent().getExtras().getString("username");
        TextView header = (TextView) findViewById(R.id.headerTextViewHm);
        header.setText(username);

        EditText searchBarTextFieldHm = (EditText) findViewById(R.id.searchBarTextFieldHm);
        ImageButton searchButtonHm = (ImageButton) findViewById(R.id.searchButtonHm);
        ImageButton notificationButtonHm = (ImageButton) findViewById(R.id.notificationButtonHm);
        Button logOutButtonHm = (Button) findViewById((R.id.logOutButtonHm));

        logOutButtonHm.setOnClickListener(new LogoutListener());
        searchButtonHm.setOnClickListener(new SearchFriendListener());
        notificationButtonHm.setOnClickListener(new OpenNotificationListener());
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

        @Override
        public void onClick(View view) {

        }
    }
}
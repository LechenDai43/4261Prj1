package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

public class Chat extends AppCompatActivity implements View.OnClickListener {
    String friend = "", name = "", email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");
        friend = getIntent().getExtras().getString("friend_name");

        TextView header = (TextView)findViewById(R.id.headerTextViewCt);
        header.setText(friend);

        ImageButton returnHomeButtonCt = (ImageButton) findViewById(R.id.returnHomeButtonCt);
        EditText inputTextFieldCt = (EditText) findViewById(R.id.inputTextFieldCt);
        Button sendButtonCt = (Button) findViewById((R.id.sendButtonCt));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.returnHomeButtonCt:
                //return back to homepage
                startActivity(new Intent(this, Home.class));
                break;

            case R.id.sendButtonCt:
                // store message in database

        }
    }

    class BackHomeListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent nextPage = new Intent(getApplicationContext(), Home.class);
            nextPage.putExtra("email", email);
            nextPage.putExtra("username", name);
            startActivity(nextPage);
        }
    }
}
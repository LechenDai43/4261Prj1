package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EditText inputTextFieldCt = (EditText) findViewById(R.id.inputTextFieldCt);

        ImageButton returnHomeButtonCt = (ImageButton) findViewById(R.id.returnHomeButtonCt);
        returnHomeButtonCt.setOnClickListener(new returnHomeListener());

        Button sendButtonCt = (Button) findViewById((R.id.sendButtonCt));
        sendButtonCt.setOnClickListener(new sendListener());

    }
}

class returnHomeListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }
}

class sendListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.os.Bundle;

public class Home extends AppCompatActivity implements View.OnClickListener  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText searchBarTextFieldHm = (EditText) findViewById(R.id.searchBarTextFieldHm);
        ImageButton searchButtonHm = (ImageButton) findViewById(R.id.searchButtonHm);
//        ImageButton notificationButtonHm = (ImageButton) findViewById(R.id.notificationButtonHm);
        Button logOutButtonHm = (Button) findViewById((R.id.logOutButtonHm));

        logOutButtonHm.setOnClickListener(this);
        searchButtonHm.setOnClickListener(this);
//        notificationButtonHm
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchButtonHm:
                //search from database
                break;
            case R.id.logOutButtonHm:
                //notify user
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }
}
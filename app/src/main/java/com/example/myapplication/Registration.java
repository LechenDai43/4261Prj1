package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity{

    EditText usernameTextFieldRg, emailTextFieldRg, passwordTextFieldRg,confirmTextFieldRg;
    Button registerButtonRg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        usernameTextFieldRg = (EditText) findViewById(R.id.usernameTextFieldRg);
//        emailTextFieldRg = (EditText) findViewById(R.id.emailTextFieldRg);
//        passwordTextFieldRg = (EditText) findViewById(R.id.passwordTextFieldRg);
//        confirmTextFieldRg = (EditText) findViewById(R.id.confirmTextFieldRg);
//        registerButtonRg = (Button) findViewById(R.id.registerButtonRg);
//        registerButtonRg.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.registerButtonRg:
//
//                break;
//        }
//    }
}
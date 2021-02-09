package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
  public class Forget extends AppCompatActivity{
//public class Forget extends AppCompatActivity implements View.OnClickListener{
    EditText emailTextFieldFg;
    Button sendEmailButtonFg, returnLoginFg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

//        emailTextFieldFg = (EditText)findViewById(R.id.emailTextFieldFg);
//        sendEmailButtonFg = (Button) findViewById(R.id.sendEmailButtonFg);
//        returnLoginFg = (Button) findViewById(R.id.returnLoginFg);

    }

//    @Override
//    public <MyActivity> void onClick(View view) {
//        if(emailTextFieldFg.equals("") || emailTextFieldFg == null) {
//            Toast.makeText(getApplicationContext(), "Email Address Empty", Toast.LENGTH_SHORT).
//        }
//            switch (view.getId()) {
//                case R.id.sendEmailButtonFg:
//                    Intent i = new Intent(Intent.ACTION_SEND);
//                    i.setType("text/plain");
//                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
//                    i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//                    i.putExtra(Intent.EXTRA_TEXT, "Your temporary passcode is 12423444");
//                    try {
//                        startActivity(Intent.createChooser(i, "Send mail..."));
//
//                    } catch (android.content.ActivityNotFoundException ex) {
//                        Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                case R.id.returnLoginFg:
//                    Intent nextPage = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(nextPage);
//
//        }
//
//    }
}
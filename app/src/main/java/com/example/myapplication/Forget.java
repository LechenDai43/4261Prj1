package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

  public class Forget extends AppCompatActivity {
      EditText emailTextFieldFg;
      Button sendEmailButtonFg, returnLoginFg;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_forget);

          emailTextFieldFg = (EditText) findViewById(R.id.emailTextFieldFg);

          sendEmailButtonFg = (Button) findViewById(R.id.sendEmailButtonFg);
          sendEmailButtonFg.setOnClickListener(new SendListener());

          returnLoginFg = (Button) findViewById(R.id.returnLoginFg);
          returnLoginFg.setOnClickListener(new returnLogInListener());

      }

      class SendListener implements View.OnClickListener {

          @Override
          public void onClick(View view) {
              String to=emailTextFieldFg.getText().toString();
              String subject="weMessage Forget Password";
              String message= " Enter your new password" ;




              //update password in db





              Intent email = new Intent(Intent.ACTION_SEND);
              email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
              email.putExtra(Intent.EXTRA_SUBJECT, subject);
              email.putExtra(Intent.EXTRA_TEXT, message);

              //need this to prompts email client only
              email.setType("message/rfc822");

              startActivity(Intent.createChooser(email, "Choose an Email client :"));

          }
      }
      class returnLogInListener implements View.OnClickListener {
          @Override
          public void onClick(View view) {
              Intent nextPage = new Intent(getApplicationContext(), MainActivity.class);
              startActivity(nextPage);
          }
      }
  }

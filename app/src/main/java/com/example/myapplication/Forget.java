package com.example.myapplication;

//import C.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;

public class Forget extends AppCompatActivity {

    EditText emailTextFieldFg;
    Button sendEmailButtonFg;
    // StringRequest stringRequest;
//      String URL = "";
    String sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        emailTextFieldFg = (EditText) findViewById(R.id.emailTextFieldFg);

        sendEmailButtonFg = (Button) findViewById(R.id.sendEmailButtonFg);
        sendEmailButtonFg.setOnClickListener(new SendListener());

//        sEmail = "sherryq1125@gmail.com";// example@gmail.com

    }

    class SendListener implements View.OnClickListener {

        @SuppressLint("LongLogTag")
        @Override
        public void onClick(View view) {
//              String email = emailTextFieldFg.getText().toString();
//              stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                  @Override
//                  public void onResponse(String response) {
//                      if (response.equals("SUCCESS")) {
//                          Toast.makeText(getApplicationContext(), "Email sent successfully, " +
//                                  "please check your email.", Toast.LENGTH_LONG).show();
//                      } else {
//                          Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
//                      }
//                  }
//              }, new Response.ErrorListener() {
//                  @Override
//                  public void onErrorResponse(VolleyError error) {
//                      Toast.makeText(Forget.this, "Please check connection", Toast.LENGTH_LONG).show();
//                  }
//              }) {
//                  @Override
//                  protected Map<String, String> getParams() throws AuthFailureError {
//                     Map <String, String> params = new HashMap<>();
//                     params.put("email", email);
//                      return super.getParams();
//                  }
//              };

//            String to = "qchen336@gatech.edu";
//            String from = "sherryq1125@gmail.com";
//            String host = "localhost";
//            Properties properties = System.getProperties();
//            properties.setProperty("mail.smtp.host", host);
//            Session session = Session.getDefaultInstance(properties);
//            try {
//                // Create a default MimeMessage object.
//                MimeMessage message = new MimeMessage(session);
//
//                // Set From: header field of the header.
//                message.setFrom(new InternetAddress(from));
//
//                // Set To: header field of the header.
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//                // Set Subject: header field
//                message.setSubject("This is the Subject Line!");
//
//                // Now set the actual message
//                message.setText("This is actual message");
//
//                // Send message
//                Transport.send(message);
//                System.out.println("Sent message successfully....");
//            } catch (MessagingException mex) {
//                mex.printStackTrace();
//            }
//        }
//    }
//}
            Log.i("Send email", "");
            String[] TO = {"qchen336@gatech.edu"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Forget.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }


        }
    }
}


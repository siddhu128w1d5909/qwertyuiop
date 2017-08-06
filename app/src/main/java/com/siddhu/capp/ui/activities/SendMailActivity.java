package com.siddhu.capp.ui.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.siddhu.capp.R;


/**
 * Created by siddhu on 4/2/2017.
 */

public class SendMailActivity extends AppCompatActivity {
    private Button mSendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mail);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mSendMail = (Button)findViewById(R.id.button);
        mSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    new SendMailAsync().execute();
                }catch (Exception e){
                    Log.d("mail",e.getLocalizedMessage());
                }




            }
        });

    }

  /*  private void sendMail(){
        GMailSender mailsender = new GMailSender("baji841@gmail.com", "manasa90322");

        String[] toArr = { "siddhu841@gmail.com", "baji841@gmail.com" };
        mailsender.set_to(toArr);
        mailsender.set_from("baji841@gmail.com");
        mailsender.set_subject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
        mailsender.setBody("Email body.");

        try {
            //mailsender.addAttachment("/sdcard/filelocation");

            if (mailsender.send()) {
               *//* Toast.makeText(SendMailActivity.this,
                        "Email was sent successfully.",
                        Toast.LENGTH_LONG).show();*//*
                Log.e("MailApp", "send email success");
            } else {
                *//*Toast.makeText(SendMailActivity.this, "Email was not sent.",
                        Toast.LENGTH_LONG).show();*//*
            }
        } catch (Exception e) {

            Log.e("MailApp", "Could not send email", e);
        }
    }*/

    private class SendMailAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SendMailActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
              //  sendMail();
            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_LONG).show();
        }

    }
}

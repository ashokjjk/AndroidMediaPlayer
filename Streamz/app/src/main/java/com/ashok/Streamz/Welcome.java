package com.ashok.Streamz;
/**
 * Author : Ashok Kumar
 * Version : 1.0
 * Organisation: Freelancer
 * Project : Music Player
 *
 * */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends AppCompatActivity {

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welc);
        getSupportActionBar().hide();

        /*
        * TO display the welcome screen for 1 second
        * */
       timer=new Timer();
       timer.schedule(new TimerTask() {
           @Override
           public void run() {
               Intent i =new Intent(Welcome.this, MainActivity.class);
               startActivity(i);
           }
       }, 1000);
    }

}

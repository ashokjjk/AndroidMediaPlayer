package com.ashok.Streamz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    Button prevSong, nextSong, playSong;
    SeekBar trackSong;
    TextView songNameLbl;
    String sName;
    static MediaPlayer mp;
    Thread thSeekbar;
    int loc;
    ArrayList<File> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        prevSong = (Button) findViewById(R.id.previous);
        nextSong = (Button) findViewById(R.id.next);
        playSong = (Button) findViewById(R.id.pause);
        songNameLbl = (TextView) findViewById(R.id.songname);
        trackSong = (SeekBar) findViewById(R.id.seekBar);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /**
         * New thread is initiliazed to take of the seek bar movement during the song playing
         */
        thSeekbar = new Thread() {
            @Override
            public void run() {

                int totalDuration = mp.getDuration();
                int currPosition = 0;

                    while (currPosition < totalDuration) {
                        try {
                            Thread.sleep(500);
                            currPosition = mp.getCurrentPosition();
                            trackSong.setProgress(currPosition);

                        } catch (InterruptedException e) {
                            e.getMessage();
                        }
                    }

            }
        };

        /*
        * Initialized a mediaplayer object that takes care of the operation
        * */
        if(mp!=null){
            mp.stop();
            mp.release();
        }
        Intent i=getIntent();
        Bundle bundle =i.getExtras();
        songs=(ArrayList) bundle.getParcelableArrayList("songs");
        sName=songs.get(loc).getName();
        String songName=i.getStringExtra("songname");
        songNameLbl.setText(songName);
        songNameLbl.setSelected(true);


        loc=bundle.getInt("pos", 0);
        Uri u =Uri.parse(songs.get(loc).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        trackSong.setMax(mp.getDuration());
        thSeekbar.start();


        trackSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(trackSong.getProgress());

            }
        });

        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackSong.setMax(mp.getDuration());
                if(mp.isPlaying()){
                    playSong.setBackgroundResource(R.drawable.play);
                    mp.pause();
                }
                else{
                    playSong.setBackgroundResource(R.drawable.pause);
                    mp.start();
                }
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();
                loc=((loc+1)%songs.size());

                Uri u =Uri.parse(songs.get(loc).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                sName=songs.get(loc).getName().toString();
                songNameLbl.setText(sName);
                mp.start();
            }
        });

        prevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();
                loc=((loc-1)<0)? songs.size()-1:loc-1;

                Uri u =Uri.parse(songs.get(loc).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                sName=songs.get(loc).getName().toString();
                songNameLbl.setText(sName);
                mp.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(),"Next Song",Toast.LENGTH_LONG).show();
                nextSong.callOnClick();
            }
        });


    }

    // Navigation for back screen to display the list of songs
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}

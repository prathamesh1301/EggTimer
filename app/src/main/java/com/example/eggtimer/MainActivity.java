package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView countdowntextView;
    SeekBar timerseekBar;
    Boolean counterIsActive=false;
    Button goButton;
    CountDownTimer countDownTimer;
    public void resetTimer(){
        counterIsActive=false;
        countdowntextView.setText("00:30");
        timerseekBar.setProgress(30);
        timerseekBar.setEnabled(true);
        goButton.setText("GO!");
        countDownTimer.cancel();
    }
    public void buttonClicked(View view){
        if(counterIsActive){
            resetTimer();
        }else {
            counterIsActive = true;
            goButton.setText("STOP");
            timerseekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerseekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minutes=secondsLeft/60;
        int seconds=secondsLeft-(minutes*60);
        String secondString=Integer.toString(seconds);
        String minuteString=Integer.toString(minutes);
        if(seconds<=9){
            secondString="0"+secondString;
        }
        if(minutes<=9){
            minuteString="0"+minuteString;
        }
        countdowntextView.setText(minuteString+":"+secondString);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerseekBar=findViewById(R.id.timerseekBar);
        countdowntextView=findViewById(R.id.countdowntextView);
        goButton=findViewById(R.id.goButton);
        timerseekBar.setMax(600);
        timerseekBar.setProgress(30);
        timerseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
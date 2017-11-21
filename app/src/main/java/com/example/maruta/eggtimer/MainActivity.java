package com.example.maruta.eggtimer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import static java.lang.Math.toIntExact;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerBar;
    private TextView timerText;
    private Button startTimer;
    private ImageView eggImg;

    private MediaPlayer mPlay;
    private boolean isOn = false;

    private CountDownTimer countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerBar = findViewById(R.id.seekBar);
        timerText = findViewById(R.id.timerText);
        startTimer = findViewById(R.id.startTimer);
        eggImg = findViewById(R.id.imageView);

        timerBar.setMax(599);

        //set default
        timerBar.setProgress(15);
        convertTime(timerBar.getProgress());

        timerBar.setOnSeekBarChangeListener(new SeekList());

        startTimer.setOnClickListener((View event) -> countdown(timerBar.getProgress()));

        mPlay = MediaPlayer.create(this, R.raw.air);



    }

    private void countdown(int countSec) {

        if(!isOn) {

            isOn = true;
            timerBar.setEnabled(false);
            startTimer.setText("stop");

            countDown = new CountDownTimer(countSec * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    convertTime((int) millisUntilFinished / 1000);
                    timerBar.setProgress((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {

                    mPlay.start();
                    eggImg.setImageResource(R.drawable.crack_egg);
                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(500);

                }

            };

            countDown.start();

        } else {

            if (eggImg.getDrawable().getConstantState() == getDrawable(R.drawable.crack_egg).getConstantState()) {
                eggImg.setImageResource(R.drawable.egg);
            }

            countDown.cancel();
            timerText.setEnabled(true);
            timerBar.setProgress(15);
            startTimer.setText("Start");
            isOn = false;

        }
    }

    private void convertTime(int secondsToConvert){

        int min = secondsToConvert  / 60;
        int sec = secondsToConvert - min * 60;

        if(sec == 0){
            timerText.setText(min + ":00" );
        } else if(sec <= 9){
            timerText.setText(min + ":0" + sec );
        } else {
            timerText.setText(min + ":" + sec);
        }


    }



    private class SeekList implements SeekBar.OnSeekBarChangeListener {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                convertTime(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
    }

}

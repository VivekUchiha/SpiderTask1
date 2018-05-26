package com.example.satsv.spidertask1;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout lay;
    TextView timer;
    long MillisecondTime, StartTime, UpdateTime;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;
    int flag;
    static int f=0;
    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (TextView) findViewById(R.id.timers);
        lay = (RelativeLayout) findViewById(R.id.lay);
        handler=new Handler();
         flag =0;

        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (flag == 0) {
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable1, 0);
                    flag = 1;
                }
                else
                if (flag == 1) {
                    handler.removeCallbacks(runnable1);
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable2, 0);
                    flag = 2;
                    f=0;
                }
                else
                if (flag == 2) {
                    handler.removeCallbacks(runnable2);
                    flag = 3;
                }
                else
                if (flag == 3) {
                    MillisecondTime = 0L;
                    StartTime = 0L;
                    UpdateTime = 0L;
                    Seconds = 0;
                    Minutes = 0;
                    MilliSeconds = 0;

                    timer.setText("15:00");
                    flag = 0;
                }

            }
        });


    }

    public Runnable runnable1 = new Runnable() {


        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = 15000 - MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);


            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText( String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds));
           if((UpdateTime <= 3000)&&(f==0))
           {
               toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
           f=1;}

            if (UpdateTime <= 0) {
                flag=2;

                handler.removeCallbacks(runnable1);
                f=0;
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable2, 0);
            }
             else
                handler.postDelayed(this, 0);



        }
    };

    public Runnable runnable2 = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };



}
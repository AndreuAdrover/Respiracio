package com.tutorialesprogramacionya.respiracio;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long[] timeToCount={10000,30000,80000,80000};//millisecons
    private int currentFase;//which conter is active
    private long currentTime;//time when the was paused
    private boolean counting;//sate of contdown false: we have to press start tre:we are counting
    private ArrayList<TextView> chFase=new ArrayList<>();
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tvFase1,tvFase2,tvFase3,tvFase4;

        tvFase1=findViewById(R.id.fase1_title);
        chFase.add((TextView)findViewById(R.id.crono1));


        tvFase2=findViewById(R.id.fase2_title);
        chFase.add((TextView)findViewById(R.id.crono2));

        tvFase3=findViewById(R.id.fase3_title);
        chFase.add((TextView)findViewById(R.id.crono3));

        tvFase4=findViewById(R.id.fase4_title);
        chFase.add((TextView)findViewById(R.id.crono4));

        button=findViewById(R.id.button);

        tvFase1.setText(getText(R.string.fase1));
        tvFase2.setText(getText(R.string.fase2));
        tvFase3.setText(getText(R.string.fase3));
        tvFase4.setText(getText(R.string.fase4));
        //initialize donDown Timers
        resetTimers(button);



    }

    public void start(View v){
        //if we press the button to pause de contdown
        if (counting) {
            //counting state false
            counting=false;
            //button text set to START
            button.setText(getString(R.string.button_text_start));
        }
        else {
            //if we press the button to start a counting
            //set the counting state to true
            counting=true;
            //if we are starting the countdowns
            if(currentTime==0)
                startCountDown(timeToCount[0],chFase.get(currentFase));
            //if we start de countdwon from a PAUSE state
            else
                startCountDown(currentTime,chFase.get(currentFase));

            //change the text from button to PAUSE
            button.setText(getString(R.string.button_text_stop));

        }

    }
    public void resetTimers(View v){


        counting=false;
        LinearLayout layout = (LinearLayout) ((ViewGroup)chFase.get(currentFase).getParent());
        layout.setBackgroundColor(Color.TRANSPARENT);
        for(int i=0;i<4;i++)
            chFase.get(i).setText(formatTime(timeToCount[i]));
        currentTime=0;
        currentFase=0;
        button.setText(getString(R.string.button_text_start));
    }

    public void startCountDown(final long inicialTime, final TextView currentTextView){
        //focus layout
        LinearLayout layout = (LinearLayout) ((ViewGroup)currentTextView.getParent());
        layout.setBackgroundResource(R.color.colorAccent);

        CountDownTimer cTimer =  new CountDownTimer(inicialTime, 1000) {

            public void onTick(long millisUntilFinished) {

                if(!counting) {
                    //RESET LAYOUT
                    this.cancel();
                }
                else{
                    currentTime = millisUntilFinished;
                    currentTextView.setText(formatTime(millisUntilFinished));
                }


            }

            public void onFinish() {
                if(counting) {
                    currentTextView.setText(formatTime(timeToCount[currentFase]));
                    currentTime = 0;
                    switch (currentFase) {
                        case 0:
                        case 1:
                        case 2:
                            currentFase++;
                            break;

                        case 3:
                            currentFase = 0;
                            break;

                    }
                    LinearLayout layout = (LinearLayout) ((ViewGroup) currentTextView.getParent());
                    layout.setBackgroundColor(Color.TRANSPARENT);
                    startCountDown(timeToCount[currentFase], chFase.get(currentFase));
                }
            }
        };
        cTimer.start();
    }

    /**
     *
     * @param time
     * @return String with MM:SS time
     */
    public String formatTime(long time){
        final int oneSecond = 1000; // in milliSeconds i.e. 1 second
        final int tenSeconds = 100000; // 100 seconds
        int totalTime = 60000; // in milliseconds i.e. 60 seconds
        String v = String.format("%02d", time/totalTime);
        int va = (int)( (time%totalTime)/oneSecond);

        return v+":"+String.format("%02d",va);

    }



}

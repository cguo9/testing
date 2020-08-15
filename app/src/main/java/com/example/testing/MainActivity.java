package com.example.testing;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 1800000;

    private TextView mTextViewCountDown;
    private Button mButtonStart;
    private Button mButtonReset;
    private Button mButtonClaim;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;  //default 30 mins

    Dialog cat1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cat1 = new Dialog(this);
        final Spinner time_select = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_select.setAdapter(myAdapter);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStart = findViewById(R.id.btn_start);
        mButtonClaim = findViewById(R.id.claim_btn);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = time_select.getSelectedItem().toString();
                switch (text){
                    case "60:00":
                        mTimeLeftInMillis = 3600000;
                        break;
                    case "90:00":
                        mTimeLeftInMillis = 5400000;
                        break;
                    case "00:05":
                        mTimeLeftInMillis = 5000;
                        break;
                    default:
                }
                mButtonStart.setVisibility(View.INVISIBLE);
                time_select.setVisibility(View.INVISIBLE);
                startTimer();
            }
        });


        updateCountDownText();
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                //popup? about the cat image
                mTextViewCountDown.setText("Congrats!");
                mButtonClaim.setVisibility(View.VISIBLE);
            }
        }.start();

    }


    private void updateCountDownText() {
        int min = (int) (mTimeLeftInMillis / 1000) / 60;
        int sec = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormat = String.format(Locale.getDefault(),"%02d:%02d", min, sec);
        mTextViewCountDown.setText(timeLeftFormat);

        System.out.println("Hello");
    }

    public void ShowPopup(View v){
        TextView txtclose;
        Button btn_home;
        Button btn_collect;
        cat1.setContentView(R.layout.custom_popup);
        txtclose = (TextView) cat1.findViewById(R.id.pop_close);
        btn_home = (Button) cat1.findViewById(R.id.btn_home);
        btn_collect = (Button) cat1.findViewById(R.id.btn_collect);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat1.dismiss();
            }
        });
        //cat1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cat1.show();
    }

}


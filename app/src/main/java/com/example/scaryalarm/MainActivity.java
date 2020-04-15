package com.example.scaryalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                openTimerActivity();
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View b){
                openScheduleActivity();
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View c){
                openAlarmActivity();
            }
        });
    }

    public void openTimerActivity(){
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }
    public void openAlarmActivity(){
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }
    public void openScheduleActivity(){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}

package com.example.scaryalarm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;

public class ScheduleActivity extends AppCompatActivity {
    Time mTime;

    Handler handler;
    Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mTime = new Time();

        r = new Runnable(){
            @Override
            public void run() {
                mTime.setToNow();
                drawingView dv = new drawingView(ScheduleActivity.this, mTime.hour, mTime.minute, mTime.second, mTime.weekDay, mTime.monthDay, getBatteryLevel());
                setContentView(dv);
                handler.postDelayed(r, 1000);
            }
        };

        handler = new Handler();
        handler.postDelayed(r, 1000);
    }

    public float getBatteryLevel(){
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if(level == -1 || scale == -1){
            return 50.0f;
        }
        return ((float) level / (float) scale) * 100.0f;
    }

    public class drawingView extends View {
        Paint mBackgroundPaint, mTextPaint, mTextPaintBack;
        Typeface tf;
        int hours, minutes, seconds, weekday, date;
        float battery;
        public drawingView(Context context, int hours, int minutes, int seconds, int weekday, int date, float battery){
            super(context);

            tf = Typeface.createFromAsset(getAssets(), "font.ttf");

            mBackgroundPaint = new Paint();
            mBackgroundPaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

            mTextPaint = new Paint();
            mTextPaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(getResources().getDimension((R.dimen.text_size)));
            mTextPaint.setTypeface(tf);

            tf = Typeface.createFromAsset(getAssets(), "font.ttf");
            mTextPaintBack = new Paint();
            mTextPaintBack.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            mTextPaintBack.setAntiAlias(true);
            mTextPaintBack.setTextAlign(Paint.Align.CENTER);
            mTextPaintBack.setTextSize(getResources().getDimension((R.dimen.text_size)));
            mTextPaintBack.setTypeface(tf);

            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            this.weekday = weekday;
            this.date = date;
            this.battery = battery;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);

            float width = canvas.getWidth();
            float height = canvas.getHeight();

            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);

            float centerX = width / 2f;
            float centerY = height / 2f;

            int cur_hour = hours;
            String cur_ampm = "AM";
            if(cur_hour == 0){
                cur_hour = 12;
            }
            if(cur_hour > 12){
                cur_hour = cur_hour - 12;
                cur_ampm = "PM";
            }

            String text = String.format("%02d:%02d:%02d", cur_hour, minutes, seconds);

            String day_of_week = "";
            if(weekday == 1){
                day_of_week = "MON";
            }
            else if(weekday == 2){
                day_of_week = "TUES";
            }
            else if(weekday == 3){
                day_of_week = "WED";
            }
            else if(weekday == 4){
                day_of_week = "THURS";
            }
            else if(weekday == 5){
                day_of_week = "FRI";
            }
            else if(weekday == 6){
                day_of_week = "SAT";
            }
            else if(weekday == 7){
                day_of_week = "SUN";
            }
            String text2 = String.format("DATE: %s %d", day_of_week, date);

            String batteryLevel = "BATTERY: " + (int) battery + "%";

            mTextPaintBack.setTextSize(getResources().getDimension((R.dimen.text_size)));
            canvas.drawText("00:00:00", centerX, centerY, mTextPaintBack);

            mTextPaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            mTextPaint.setTextSize(getResources().getDimension((R.dimen.text_size)));
            canvas.drawText(text, centerX, centerY, mTextPaint);

            mTextPaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            mTextPaint.setTextSize(getResources().getDimension((R.dimen.text_size_small)));
            canvas.drawText(batteryLevel + " " + text2, centerX, centerY + getResources().getDimension(R.dimen.text_size_small), mTextPaint);
        }
    }
}

package com.sparkles.clapapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float v = event.values[0];

        Log.i("Sensor ",""+v);
        if(v == 0.0){
            Log.i("Sensor ","Starting sound");
            mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            mp.start();
        }else{
            Log.i("Sensor ", "Stopping sound");
            if(mp!=null) {
                Log.i("Song", "Stopped");
                mp.stop();
                mp.release();
                mp = null;
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if(mp!=null) {
            mp.release();
            mp = null;
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(mp!=null){
            mp.release();
            mp = null;
        }
    }

}

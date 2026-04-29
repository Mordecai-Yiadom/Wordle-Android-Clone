package com.example.finalproject.wordle.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.finalproject.wordle.sensor.event.ElevationSensorChangedEvent;
import com.example.finalproject.wordle.sensor.event.ElevationSensorListener;

import java.util.ArrayList;
import java.util.List;

public class ElevationSensor implements SensorEventListener
{
    private Sensor accelerometer;

    private float currentElevation;
    private SensorManager sensorManager;

    private static final float DEFAULT_ELEVATION = 9.81f;

    private List<ElevationSensorListener> changeListeners;


    public ElevationSensor(Activity activity)
    {

        changeListeners = new ArrayList<>();
        currentElevation = DEFAULT_ELEVATION;

        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void addChangeListener(ElevationSensorListener listener)
    {
        changeListeners.add(listener);
    }

    public void removeChangeListener(ElevationSensorListener listener)
    {
        changeListeners.remove(listener);
    }

    public float getCurrentElevation()
    {
        return currentElevation;
    }

    private void notifyChangeListeners(ElevationSensorChangedEvent event)
    {
        for(ElevationSensorListener listener : changeListeners)
        {
            listener.onElevationChanged(event);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int sensorType = event.sensor.getType();
        if(sensorType != Sensor.TYPE_ACCELEROMETER) return;

        float newElevation = event.values[1];

        notifyChangeListeners(new ElevationSensorChangedEvent(this, currentElevation, newElevation));
        currentElevation = newElevation;
    }
}

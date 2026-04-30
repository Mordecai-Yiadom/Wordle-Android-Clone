package com.example.finalproject.wordle.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.finalproject.wordle.sensor.event.LightSensorChangedEvent;
import com.example.finalproject.wordle.sensor.event.LightSensorListener;

import java.util.ArrayList;
import java.util.List;

public class LightSensor implements SensorEventListener {
    private Sensor lightSensor;

    private float currentLuxLevel;
    private SensorManager sensorManager;

    private static final float DEFAULT_LUX = 0;
    private static final int SENSOR_TYPE = Sensor.TYPE_LIGHT;

    public static final float MAX_LUX_LEVEL = 40_000;

    private static float GLOBAL_LUX_LEVEL = 0;

    private List<LightSensorListener> changeListeners;


    public LightSensor(Activity activity) {

        changeListeners = new ArrayList<>();
        currentLuxLevel = DEFAULT_LUX;

        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(SENSOR_TYPE);

        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        currentLuxLevel = GLOBAL_LUX_LEVEL;

    }

    public void addChangeListener(LightSensorListener listener) {
        changeListeners.add(listener);
    }

    public void removeChangeListener(LightSensorListener listener) {
        changeListeners.remove(listener);
    }

    public float getCurrentLuxLevel() {
        return currentLuxLevel;
    }

    private void notifyChangeListeners(LightSensorChangedEvent event)
    {
        for(LightSensorListener listener : changeListeners)
        {
            listener.onLuxLevelChanged(event);
        }
    }

    public void destroy()
    {
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int sensorType = event.sensor.getType();
        if(sensorType != SENSOR_TYPE) return;

        float newLuxLevel = event.values[0];

        notifyChangeListeners(new LightSensorChangedEvent(this, currentLuxLevel, newLuxLevel));
        currentLuxLevel = newLuxLevel;
        GLOBAL_LUX_LEVEL = newLuxLevel;
    }
}

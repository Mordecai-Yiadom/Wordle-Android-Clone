package com.example.finalproject.wordle.sensor.event;

import com.example.finalproject.wordle.sensor.LightSensor;

public class LightSensorChangedEvent
{
    private LightSensor sensor;
    private float oldElevation;
    private float newElevation;
    public LightSensorChangedEvent(LightSensor sensor, float oldLuxLevel, float newLuxLevel)
    {
        this.sensor = sensor;
        this.oldElevation = oldLuxLevel;
        this.newElevation = newLuxLevel;
    }

    public LightSensor getSensor()
    {
        return sensor;
    }

    public float getOldLuxLevel()
    {
        return oldElevation;
    }

    public float getNewLuxLevel()
    {
        return newElevation;
    }
}

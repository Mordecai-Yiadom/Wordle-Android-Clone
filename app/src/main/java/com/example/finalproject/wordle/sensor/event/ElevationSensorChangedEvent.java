package com.example.finalproject.wordle.sensor.event;

import com.example.finalproject.wordle.sensor.ElevationSensor;

public class ElevationSensorChangedEvent
{
    private ElevationSensor sensor;
    private float oldElevation;
    private float newElevation;
    public ElevationSensorChangedEvent(ElevationSensor sensor, float oldElevation, float newElevation)
    {
        this.sensor = sensor;
        this.oldElevation = oldElevation;
        this.newElevation = newElevation;
    }

    public ElevationSensor getSensor()
    {
        return sensor;
    }

    public float getOldElevation()
    {
        return oldElevation;
    }

    public float getNewElevation()
    {
        return newElevation;
    }
}

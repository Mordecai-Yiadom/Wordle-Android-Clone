package com.example.finalproject.wordle.sensor.event;

public interface LightSensorListener
{
    void onLuxLevelChanged(LightSensorChangedEvent event);
}

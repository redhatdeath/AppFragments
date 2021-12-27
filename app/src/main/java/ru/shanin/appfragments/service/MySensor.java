package ru.shanin.appfragments.service;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

public class MySensor {
    private final Sensor sensor;
    private final int sensorType;
    private SensorEventListener listener;
    private final MutableLiveData<String> sensorLiveData;

    public MySensor(SensorManager sensorManager, int sensorType) {
        this.sensorType = sensorType;
        sensor = sensorManager.getDefaultSensor(this.sensorType);
        sensorLiveData = new MutableLiveData<>();
        if (sensor == null)
            sensorLiveData.postValue("There is no Sensor with Type = " + this.sensorType);
        else
            listener = this.SettingListener(sensorLiveData);
    }

    private SensorEventListener SettingListener(MutableLiveData<String> liveData) {
        return new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                liveData.postValue(String.valueOf(event.values[0]));
            }
        };
    }

    private void onStartListenSens(SensorManager sensorManager) {
        sensorManager
                .registerListener(
                        listener,
                        sensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                );
    }

    private void onStopListenSens(SensorManager sensorManager) {
        sensorManager
                .unregisterListener(
                        listener,
                        sensor
                );
    }

    public MutableLiveData<String> getSensorLiveData() {
        return sensorLiveData;
    }

    public void startWork(SensorManager sensorManager) {
        if (sensor != null)
            onStartListenSens(sensorManager);
        else
            Log.d(
                    MySensor.class.getSimpleName(),
                    "There is no Sensor for start watching" +
                            " with Type = " + this.sensorType + " ..."
            );
    }

    public void stopWork(SensorManager sensorManager) {
        if (sensor != null)
            onStopListenSens(sensorManager);
        else
            Log.d(
                    MySensor.class.getSimpleName(),
                    "There is no Sensor for stop watching " +
                            "with Type = " + this.sensorType + "..."
            );
    }
}
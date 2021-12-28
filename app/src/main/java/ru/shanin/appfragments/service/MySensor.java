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

    public MySensor(int sensorType) {
        Log.d("SensorsFragment", "MySensorsService: MySensor: input data = " + sensorType);
        this.sensorType = sensorType;
        sensor = MySensorsService.getSensorManager().getDefaultSensor(this.sensorType);
        Log.d("SensorsFragment", "MySensorsService: MySensor: sensor = " + sensor.getName());
        sensorLiveData = new MutableLiveData<>();
        if (sensor == null)
            sensorLiveData.postValue("There is no Sensor with Type = " + this.sensorType);
        else
            listener = this.SettingListener(sensorLiveData);
    }

    private SensorEventListener SettingListener(MutableLiveData<String> liveData) {
        Log.d("SensorsFragment", "MySensorsService: MySensor: sensor = " + sensor.getName()
        +" listener = new create");
        return new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.d("SensorsFragment", "onSensorChanged: " + event.values[0]);
                liveData.postValue(String.valueOf(event.values[0]));
            }
        };
    }

    private void onStartListenSens() {
        MySensorsService.getSensorManager()
                .registerListener(
                        listener,
                        sensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                );
    }

    private void onStopListenSens() {
        MySensorsService.getSensorManager()
                .unregisterListener(
                        listener,
                        sensor
                );
    }

    public MutableLiveData<String> getSensorLiveData() {
        return sensorLiveData;
    }

    public void startWork() {
        Log.d("SensorsFragment", "MySensorsService: MySensor: sensor = " + sensor.getName()
        +"startWork");
        if (sensor != null)
            onStartListenSens();
        else
            Log.d(
                    MySensor.class.getSimpleName(),
                    "There is no Sensor for start watching" +
                            " with Type = " + this.sensorType + " ..."
            );
    }

    public void stopWork() {
        if (sensor != null)
            onStopListenSens();
        else
            Log.d(
                    MySensor.class.getSimpleName(),
                    "There is no Sensor for stop watching " +
                            "with Type = " + this.sensorType + "..."
            );
    }
}
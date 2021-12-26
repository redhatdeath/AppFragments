package ru.shanin.appfragments.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class TypeLightService extends Service {

    private SensorManager sensorManager;
    private Sensor sensorTypeLight;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public TypeLightService getService() {
            return TypeLightService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(
                this,
                "Служба создана TypeLight",
                Toast.LENGTH_SHORT
        ).show();
        initSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(
                this,
                "Служба остановлена TypeLight",
                Toast.LENGTH_SHORT
        ).show();
    }


    public static Intent onStartService(Context context) {
        return new Intent(context, TypeLightService.class);
    }

    public static Intent onStopService(Context context) {
        return new Intent(context, TypeLightService.class);
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager == null)
            throw new RuntimeException("There is no sensors...");
        sensorTypeLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorTypeLight == null)
            throw new RuntimeException("There is no Light Sensor.");
    }

    private MutableLiveData<String> sensorValueLiveData = new MutableLiveData<>();
    private String sensorValue;
    SensorEventListener listenerLight = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            sensorValue = String.valueOf(event.values[0]);
            Log.d("TypeLightService", "sensor value = " + sensorValue);
            sensorValueLiveData.postValue(sensorValue);
        }
    };

    public void onStartListenLightSens() {
        sensorManager.registerListener(
                listenerLight,
                sensorTypeLight,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void onStopListenLightSens() {
        sensorManager.unregisterListener(listenerLight, sensorTypeLight);
    }

    public MutableLiveData<String> getSensorValueLiveData() {
        return sensorValueLiveData;
    }
}

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

import java.util.List;

public class SensorsService extends Service {

    private static SensorManager sensorManager;
    private Sensor sensorTypeLight;
    private Sensor sensorTypeAccelerometer;
    private Sensor sensorTypeGyroscope;
    private Sensor sensorTypeMagneticField;
    private final IBinder binder = new LocalBinder();

    private MutableLiveData<List<Sensor>> listOfSensorsLiveData;
    private MutableLiveData<String> sensorValueTypeLightLiveData;
    private MutableLiveData<String> sensorValueTypeAccelerometerLiveData;
    private MutableLiveData<String> sensorValueTypeGyroscopeLiveData;
    private MutableLiveData<String> sensorValueTypeMagneticFieldLiveData;
    private SensorEventListener accelerometerListener;
    private SensorEventListener gyroscopeListener;
    private SensorEventListener lightListener;
    private SensorEventListener magneticFieldListener;


    private static final String _CREATE_SERVICE = "Служба создана SensorsService";
    private static final String _START_SERVICE = "Служба запущена SensorsService";
    private static final String _STOP_SERVICE = "Служба остановлена SensorsService";

    public class LocalBinder extends Binder {
        public SensorsService getService() {
            return SensorsService.this;
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
        Log.d(SensorsService.class.getSimpleName(), _CREATE_SERVICE);
        Toast.makeText(this, _CREATE_SERVICE, Toast.LENGTH_SHORT).show();
        initLiveData();
        initSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(SensorsService.class.getSimpleName(), _STOP_SERVICE);
        Toast.makeText(this, _STOP_SERVICE, Toast.LENGTH_SHORT).show();
    }


    public static Intent onStartService(Context context) {
        Log.d(SensorsService.class.getSimpleName(), _START_SERVICE);
        Toast.makeText(context, _START_SERVICE, Toast.LENGTH_SHORT).show();
        return new Intent(context, SensorsService.class);
    }

    public static Intent onStopService(Context context) {
        return new Intent(context, SensorsService.class);
    }


    private void initLiveData() {
        listOfSensorsLiveData = new MutableLiveData<>();
        sensorValueTypeAccelerometerLiveData = new MutableLiveData<>();
        sensorValueTypeGyroscopeLiveData = new MutableLiveData<>();
        sensorValueTypeLightLiveData = new MutableLiveData<>();
        sensorValueTypeMagneticFieldLiveData = new MutableLiveData<>();
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager == null)
            throw new RuntimeException("There is no sensors...");
        // get list of sensors
        listOfSensorsLiveData.postValue(sensorManager.getSensorList(Sensor.TYPE_ALL));
        // Accelerometer
        sensorTypeAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorTypeAccelerometer == null) {
            sensorValueTypeAccelerometerLiveData.postValue("There is no Accelerometer Sensor");
        } else accelerometerListener = SettingListener(sensorValueTypeAccelerometerLiveData);
        // Gyroscope
        sensorTypeGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (sensorTypeGyroscope == null) {
            sensorValueTypeGyroscopeLiveData.postValue("There is no Gyroscope Sensor");
        } else gyroscopeListener = SettingListener(sensorValueTypeGyroscopeLiveData);
        // Light
        sensorTypeLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorTypeLight == null) {
            sensorValueTypeLightLiveData.postValue("There is no Light Sensor");
        } else lightListener = SettingListener(sensorValueTypeLightLiveData);
        // Magnetic Field
        sensorTypeMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensorTypeMagneticField == null) {
            sensorValueTypeMagneticFieldLiveData.postValue("There is no Magnetic Field Sensor");
        } else magneticFieldListener = SettingListener(sensorValueTypeMagneticFieldLiveData);
    }

    private void onStartListenSens(Sensor sensor, SensorEventListener sensorEventListener) {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    private void onStopListenSens(Sensor sensor, SensorEventListener sensorEventListener) {
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }

    private SensorEventListener SettingListener(MutableLiveData<String> liveData) {
        SensorEventListener eventListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                liveData.postValue(String.valueOf(event.values[0]));
            }
        };
        return eventListener;
    }

    // List of all service
    public MutableLiveData<List<Sensor>> getListOfSensorsLiveData() {
        return listOfSensorsLiveData;
    }

    // Accelerometer
    public void onStartListen_Accelerometer() {
        sensorManager.registerListener(
                accelerometerListener,
                sensorTypeAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void onStopListen_Accelerometer() {
        sensorManager.unregisterListener(accelerometerListener, sensorTypeAccelerometer);
    }

    public MutableLiveData<String> getSensorValueTypeAccelerometerLiveData() {
        return sensorValueTypeAccelerometerLiveData;
    }

    // Gyroscope
    public void onStartListen_Gyroscope() {
        sensorManager.registerListener(
                gyroscopeListener,
                sensorTypeGyroscope,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void onStopListen_Gyroscope() {
        sensorManager.unregisterListener(gyroscopeListener, sensorTypeGyroscope);
    }

    public MutableLiveData<String> getSensorValueTypeGyroscopeLiveData() {
        return sensorValueTypeGyroscopeLiveData;
    }

    // Light
    public void onStartListen_Light() {
        sensorManager.registerListener(
                lightListener,
                sensorTypeLight,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void onStopListen_Light() {
        sensorManager.unregisterListener(lightListener, sensorTypeLight);
    }

    public MutableLiveData<String> getSensorValueTypeLightLiveData() {
        return sensorValueTypeLightLiveData;
    }

    // MagneticFiel
    public void onStartListen_MagneticFiel() {
        sensorManager.registerListener(
                magneticFieldListener,
                sensorTypeMagneticField,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void onStopListen_MagneticField() {
        sensorManager.unregisterListener(magneticFieldListener, sensorTypeMagneticField);
    }

    public MutableLiveData<String> getSensorValueTypeMagneticFieldLiveData() {
        return sensorValueTypeMagneticFieldLiveData;
    }
}

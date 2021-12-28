package ru.shanin.appfragments.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MySensorsService extends Service {

    private static SensorManager sensorManager;

    private MySensor mySensor;
    private MutableLiveData<String> mySensorDataLiveData;

    public boolean newMySensor(int sensorType) {
        mySensorDataLiveData = new MutableLiveData<>();
        mySensor = new MySensor(sensorType, mySensorDataLiveData);
        return true;
    }


    private final IBinder binder = new LocalBinder();

    private MutableLiveData<List<Sensor>> listOfSensorsLiveData;
    private static final String _CREATE_SERVICE = "Служба создана SensorsService";
    private static final String _START_SERVICE = "Служба запущена SensorsService";
    private static final String _STOP_SERVICE = "Служба остановлена SensorsService";

    public class LocalBinder extends Binder {
        public MySensorsService getService() {
            return MySensorsService.this;
        }
    }

    public static SensorManager getSensorManager() {
        return sensorManager;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public static Intent onStartService(Context context) {
        Log.d(MySensorsService.class.getSimpleName(), _START_SERVICE);
        return new Intent(context, MySensorsService.class);
    }

    public static Intent onStopService(Context context) {
        Log.d(MySensorsService.class.getSimpleName(), _STOP_SERVICE);
        return new Intent(context, MySensorsService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MySensorsService.class.getSimpleName(), _CREATE_SERVICE);
        initSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager == null)
            throw new RuntimeException("There is no sensors...");
        // get list of sensors
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        listOfSensorsLiveData = new MutableLiveData<>();
        listOfSensorsLiveData.postValue(sensors);
    }

    public MutableLiveData<List<Sensor>> getListOfSensorsLiveData() {
        return listOfSensorsLiveData;
    }

    public MutableLiveData<String> getMySensorDataLiveData() {
        return mySensorDataLiveData;
    }


    public void startMySensorListener() {
        mySensor.startWork();
    }

    public void stopMySensorListener() {
        mySensor.stopWork();
    }
}


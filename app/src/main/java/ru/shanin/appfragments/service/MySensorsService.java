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

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MySensorsService extends Service {

    private static MySensor accel;
    private static MySensor gyros;
    private static MySensor light;
    private static MySensor magne;
    private static SensorManager sensorManager;

    private final IBinder binder = new LocalBinder();

    private MutableLiveData<List<Sensor>> listOfSensorsLiveData;
    private MutableLiveData<String> accelDataLiveData;
    private MutableLiveData<String> gyrosDataLiveData;
    private MutableLiveData<String> lightDataLiveData;
    private MutableLiveData<String> magneDataLiveData;


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
        // sensor
        accelDataLiveData = new MutableLiveData<>();
        gyrosDataLiveData = new MutableLiveData<>();
        lightDataLiveData = new MutableLiveData<>();
        magneDataLiveData = new MutableLiveData<>();
        accel = new MySensor(Sensor.TYPE_ACCELEROMETER, accelDataLiveData);
        gyros = new MySensor(Sensor.TYPE_GYROSCOPE, gyrosDataLiveData);
        light = new MySensor(Sensor.TYPE_LIGHT, lightDataLiveData);
        magne = new MySensor(Sensor.TYPE_MAGNETIC_FIELD, magneDataLiveData);
    }

    public MutableLiveData<List<Sensor>> getListOfSensorsLiveData() {
        return listOfSensorsLiveData;
    }

    public MutableLiveData<String> getAccelDataLiveData() {
        return accelDataLiveData;
    }

    public MutableLiveData<String> getGyrosDataLiveData() {
        return gyrosDataLiveData;
    }

    public MutableLiveData<String> getLightDataLiveData() {
        return lightDataLiveData;
    }

    public MutableLiveData<String> getMagneDataLiveData() {
        return magneDataLiveData;
    }

    public void startListener(int sensorType) {
        Log.d("SensorsFragment", "startListener sensorType listener type = " + sensorType);
        String name = "";
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accel.startWork();
                name = " " + accel.sensor.getName() + " ";
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyros.startWork();
                name = " " + gyros.sensor.getName() + " ";
                break;
            case Sensor.TYPE_LIGHT:
                light.startWork();
                name = " " + light.sensor.getName() + " ";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magne.startWork();
                name = " " + magne.sensor.getName() + " ";
                break;
        }
        Log.d("SensorsFragment", "sensorType listener name = " + name);
    }

    public void stopListeners() {
        accel.stopWork();
        gyros.stopWork();
        light.stopWork();
        magne.stopWork();
    }


    class MySensor {
        private final Sensor sensor;
        private final int sensorType;
        private SensorEventListener listener;

        public MySensor(int sensorType, MutableLiveData<String> sensorLiveData) {
            this.sensorType = sensorType;
            sensor = MySensorsService.getSensorManager().getDefaultSensor(this.sensorType);
            Log.d("SensorsFragment", "MySensorsService: MySensor: sensor = " + sensor.getName());
            if (sensor == null)
                sensorLiveData.postValue("There is no Sensor with Type = " + this.sensorType);
            else
                this.listener = this.SettingListener(sensorLiveData);
        }

        private SensorEventListener SettingListener(MutableLiveData<String> liveData) {
            return new SensorEventListener() {
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }

                @Override
                public void onSensorChanged(SensorEvent event) {
                    liveData.setValue(String.valueOf(event.values[0]));
                }
            };
        }

        private void onStartListenSens() {
            sensorManager.registerListener(
                    listener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }

        private void onStopListenSens() {
            sensorManager.unregisterListener(
                    listener,
                    sensor
            );
        }

        public void startWork() {
            Log.d("SensorsFragment", "MySensorsService: MySensor: sensor = " + sensor.getName()
                    + " startWork");
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
            Log.d("SensorsFragment", "MySensorsService: MySensor: sensor = " + sensor.getName()
                    + " stopWork");
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

}


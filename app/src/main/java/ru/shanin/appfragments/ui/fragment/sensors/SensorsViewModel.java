package ru.shanin.appfragments.ui.fragment.sensors;

import android.hardware.Sensor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.shanin.appfragments.app.AppStart;

public class SensorsViewModel extends ViewModel {

    public MutableLiveData<List<Sensor>> sensorsData = AppStart.mService.getListOfSensorsLiveData();
    public MutableLiveData<String> mySensorData = AppStart.mService.getMySensorDataLiveData();


}

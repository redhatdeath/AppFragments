package ru.shanin.appfragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelResult extends ViewModel {

    public MutableLiveData<String> sensorData = AppStart.mService.getSensorValueLiveData();


}

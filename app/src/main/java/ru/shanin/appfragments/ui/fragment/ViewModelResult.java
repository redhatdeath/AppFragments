package ru.shanin.appfragments.ui.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.shanin.appfragments.app.AppStart;

public class ViewModelResult extends ViewModel {

    public MutableLiveData<String> sensorData = AppStart.mService.getSensorValueLiveData();


}

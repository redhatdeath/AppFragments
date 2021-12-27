package ru.shanin.appfragments.app;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import ru.shanin.appfragments.service.SensorsService;

public class AppStart extends Application {
    public static SensorsService mService;


    @Override
    public void onCreate() {
        super.onCreate();
        onStartAppService(this);
        initService();
    }

    private void onStartAppService(Context context) {
        startService(SensorsService.onStartService(context));
    }


    private void initService() {
        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(
                    ComponentName className,
                    IBinder service
            ) {
                SensorsService.LocalBinder binder = (SensorsService.LocalBinder) service;
                mService = binder.getService();
                mService.onStartListenLightSens();
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                mService.onStopListenLightSens();
            }
        };
        // Bind to LocalService
        Intent intent = new Intent(this, SensorsService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


}

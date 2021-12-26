package ru.shanin.appfragments;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import ru.shanin.appfragments.service.TypeLightService;

public class AppStart extends Application {
    public static TypeLightService mService;


    @Override
    public void onCreate() {
        super.onCreate();
        onStartService(this);
        initService();
    }

    private void onStartService(Context context) {
        startService(TypeLightService.onStartService(context));
    }


    private void initService() {

        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(
                    ComponentName className,
                    IBinder service
            ) {
                TypeLightService.LocalBinder binder = (TypeLightService.LocalBinder) service;
                mService = binder.getService();
                mService.onStartListenLightSens();
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                mService.onStopListenLightSens();
            }
        };
        // Bind to LocalService
        Intent intent = new Intent(this, TypeLightService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }


}

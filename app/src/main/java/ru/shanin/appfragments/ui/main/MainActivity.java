package ru.shanin.appfragments.ui.main;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import ru.shanin.appfragments.R;
import ru.shanin.appfragments.service.MySensorsService;
import ru.shanin.appfragments.ui.fragment.result.ResultFragment;
import ru.shanin.appfragments.ui.fragment.sensors.SensorsFragment;
import ru.shanin.appfragments.ui.noland.ActivityResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt, bt_list, bt_accel, bt_gyros, bt_light, bt_magne;
    private EditText et;
    private FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt:
                String input = et.getText().toString();
                if (isOnePaneMode())
                    launchActivityResult(input);
                else
                    launchFragmentResult(input);
                break;
            case R.id.bt_list:
                if (isOnePaneMode())
                    launchActivityResult(Sensor.TYPE_ALL);
                else
                    launchFragmentSensors(Sensor.TYPE_ALL);
                break;
            case R.id.bt_accel:
                if (isOnePaneMode())
                    launchActivityResult(Sensor.TYPE_ACCELEROMETER);
                else
                    launchFragmentSensors(Sensor.TYPE_ACCELEROMETER);
                break;
            case R.id.bt_gyros:
                if (isOnePaneMode())
                    launchActivityResult(Sensor.TYPE_GYROSCOPE);
                else
                    launchFragmentSensors(Sensor.TYPE_GYROSCOPE);
                break;
            case R.id.bt_light:
                if (isOnePaneMode())
                    launchActivityResult(Sensor.TYPE_LIGHT);
                else
                    launchFragmentSensors(Sensor.TYPE_LIGHT);
                break;
            case R.id.bt_magne:
                if (isOnePaneMode())
                    launchActivityResult(Sensor.TYPE_MAGNETIC_FIELD);
                else
                    launchFragmentSensors(Sensor.TYPE_MAGNETIC_FIELD);
                break;
        }
    }


    private void initLayout() {
        fragmentContainerView = findViewById(R.id.fragmentActivityResult);
        et = findViewById(R.id.et);
        bt = findViewById(R.id.bt);
        bt_list = findViewById(R.id.bt_list);
        bt_accel = findViewById(R.id.bt_accel);
        bt_gyros = findViewById(R.id.bt_gyros);
        bt_light = findViewById(R.id.bt_light);
        bt_magne = findViewById(R.id.bt_magne);
        bt.setOnClickListener(this);
        bt_list.setOnClickListener(this);
        bt_accel.setOnClickListener(this);
        bt_gyros.setOnClickListener(this);
        bt_light.setOnClickListener(this);
        bt_magne.setOnClickListener(this);
    }

    private boolean isOnePaneMode() {
        return fragmentContainerView == null;
    }

    private void launchActivityResult(String data) {
        Intent i = ActivityResult.newIntentActivityResult(
                getApplicationContext(), data);
        startActivity(i);
    }

    private void launchActivityResult(int data) {
        Intent i = ActivityResult.newIntentActivityResult(
                getApplicationContext(), data);
        startActivity(i);
    }

    private void launchFragmentResult(String data) {
        Fragment fragment;
//        if(bla bla bla){}
//        else {}
        fragment = ResultFragment.newInstanceWithInputData(data);
        getSupportFragmentManager()
                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("null")
                .replace(
                        R.id.fragmentActivityResult,
                        ResultFragment.newInstanceWithInputData(data)
                )
                .add(
                        R.id.fragmentActivityResult,
                        fragment
                )
                .commit();
    }

    private void launchFragmentSensors(int data) {
        Fragment fragment;
//        if(bla bla bla){}
//        else {}
        fragment = SensorsFragment.newInstanceWithInputData(data);
        getSupportFragmentManager()
                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("null")
                .replace(
                        R.id.fragmentActivityResult,
                        SensorsFragment.newInstanceWithInputData(data)
                )
//                .add(
//                        R.id.fragmentActivityResult,
//                        fragment
//                )
                .commit();
    }

    @Override
    protected void onDestroy() {
        stopService(MySensorsService.onMySensorsService(getApplicationContext()));
        super.onDestroy();
    }
}
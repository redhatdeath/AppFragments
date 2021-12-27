package ru.shanin.appfragments.ui.main;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private Button bt;
    private EditText et;
    private FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        bt.setOnClickListener(v -> {
            String input = et.getText().toString();
            if (isOnePaneMode())
                launchActivityResult(input);
            else
                launchFragmentSensors();
        });
    }


    private void initLayout() {
        bt = findViewById(R.id.bt);
        et = findViewById(R.id.et);
        fragmentContainerView = findViewById(R.id.fragmentActivityResult);
    }

    private boolean isOnePaneMode() {
        return fragmentContainerView == null;
    }

    private void launchActivityResult(String data) {
        Intent i = ActivityResult.newIntentActivityResult(
                getApplicationContext(),
                data
        );
        startActivity(i);
    }

    private void launchFragmentResult(String data) {
        Fragment fragment;
//        if(bla bla bla){}
//        else {}
        fragment = ResultFragment.newInstanceWithInputData(data);
//        getSupportFragmentManager()
//                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
//                .addToBackStack("null")
//                .replace(
//                        R.id.fragmentActivityResult,
//                        FragmentResult.newInstanceWithInputData(data)
//                )
                .add(
                        R.id.fragmentActivityResult,
                        fragment
                )
                .commit();
    }

    private void launchFragmentSensors() {
        Fragment fragment;
//        if(bla bla bla){}
//        else {}
        fragment = SensorsFragment.newInstanceWithoutInputData();
//        getSupportFragmentManager()
//                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
//                .addToBackStack("null")
//                .replace(
//                        R.id.fragmentActivityResult,
//                        FragmentResult.newInstanceWithInputData(data)
//                )
                .add(
                        R.id.fragmentActivityResult,
                        fragment
                )
                .commit();
    }

    @Override
    protected void onDestroy() {
        stopService(MySensorsService.onStopService(getApplicationContext()));
        super.onDestroy();


    }
}
package ru.shanin.appfragments.ui.fragment.sensors;

import android.hardware.Sensor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.shanin.appfragments.R;
import ru.shanin.appfragments.app.AppStart;
import ru.shanin.appfragments.service.MySensorsService;

public class SensorsFragment extends Fragment {

    private static final String ARGUMENT_FROM_INPUT_INT = "input_Int";
    private static final int ARGUMENT_FROM_INPUT_TEXT_DEFAULT = Sensor.REPORTING_MODE_CONTINUOUS;
    private int sensorType;

    private static MySensorsService mService;
    private TextView tv_sensor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = AppStart.mService;
        parseParams();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_sensors,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
        SensorsViewModel viewModel = new ViewModelProvider(this).get(SensorsViewModel.class);
        viewModel.sensorsData.observe(getViewLifecycleOwner(), new MyObserver());

    }

    public static SensorsFragment newInstanceWithInputData(int input) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_FROM_INPUT_INT, input);
        SensorsFragment fragment = new SensorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseParams() {
        sensorType = ARGUMENT_FROM_INPUT_TEXT_DEFAULT;
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_FROM_INPUT_INT))
            throw new RuntimeException("Arguments are absent");
        sensorType = args.getInt(ARGUMENT_FROM_INPUT_INT);
    }


    private void initLayout(View view) {
        tv_sensor = view.findViewById(R.id.about_tv);
        (view.findViewById(R.id.bt)).setOnClickListener(v -> requireActivity().onBackPressed());
    }


    private class MyObserver implements Observer<List<Sensor>> {
        @Override
        public void onChanged(List<Sensor> sensorsList) {
            StringBuilder result = new StringBuilder("Sensors:\n");
            if (!sensorsList.isEmpty()) {
                for (Sensor s : sensorsList)
                    result = result.append(s.getName()).append("\n");
            } else {
                result = result.append("null\n");
            }
            tv_sensor.setText(result.toString());
        }
    }

    private void initServiceConnect() {

    }


}
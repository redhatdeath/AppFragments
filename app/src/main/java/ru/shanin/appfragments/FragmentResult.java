package ru.shanin.appfragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class FragmentResult extends Fragment {

    private static final String ARGUMENT_FROM_INPUT_TEXT = "input_text";
    private static final String ARGUMENT_FROM_INPUT_TEXT_DEFAULT = "empty_text";
    private String input_data;

    private TextView tv_input, tv_sensor;
    private Button bt;

    private ViewModelResult viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseParams();
        Log.d("FragmentResult", "onCreate");


    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_result,
                container,
                false
        );
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
        viewModel = new ViewModelProvider(this).get(ViewModelResult.class);
        viewModel.sensorData.observe(getViewLifecycleOwner(), new MyObserver());

    }

    private void parseParams() {
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_FROM_INPUT_TEXT))
            throw new RuntimeException("Arguments are absent");
        input_data = args.getString(ARGUMENT_FROM_INPUT_TEXT, ARGUMENT_FROM_INPUT_TEXT_DEFAULT);
    }


    public static FragmentResult newInstanceWithInputData(String input) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_FROM_INPUT_TEXT, input);
        FragmentResult fragment = new FragmentResult();
        fragment.setArguments(args);
        return fragment;

    }

    private void initLayout(View view) {
        tv_sensor = view.findViewById(R.id.sensor_tv);
        tv_input = view.findViewById(R.id.about_tv);
        tv_input.setText(input_data);
        bt = view.findViewById(R.id.bt);
        bt.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

    }


    private class MyObserver implements Observer<String> {
        @Override
        public void onChanged(String sensorValue) {
            tv_sensor.setText(sensorValue);
        }
    }


}

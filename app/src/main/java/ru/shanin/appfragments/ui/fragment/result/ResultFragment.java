package ru.shanin.appfragments.ui.fragment.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.shanin.appfragments.R;

public class ResultFragment extends Fragment {

    private static final String ARGUMENT_FROM_INPUT_TEXT = "input_text";
    private static final String ARGUMENT_FROM_INPUT_TEXT_DEFAULT = "empty_text";
    private String input_data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                R.layout.fragment_result,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
    }

    private void parseParams() {
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_FROM_INPUT_TEXT))
            throw new RuntimeException("Arguments are absent");
        input_data = args.getString(ARGUMENT_FROM_INPUT_TEXT, ARGUMENT_FROM_INPUT_TEXT_DEFAULT);
    }

    public static ResultFragment newInstanceWithInputData(String input) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_FROM_INPUT_TEXT, input);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initLayout(View view) {
        TextView tv_input = view.findViewById(R.id.about_tv);
        tv_input.setText(input_data);
        Button bt = view.findViewById(R.id.bt);
        bt.setOnClickListener(v -> requireActivity().onBackPressed());
    }
}

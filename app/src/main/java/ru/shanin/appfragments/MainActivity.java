package ru.shanin.appfragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

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
                launchFragmentResult(input);
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
        getSupportFragmentManager()
                .beginTransaction()
//                .replace(
//                        R.id.fragmentActivityResult,
//                        FragmentResult.newInstanceWithInputData(data)
//                )
                .add(
                        R.id.fragmentActivityResult,
                        FragmentResult.newInstanceWithInputData(data)
                )
                .commitNow();
    }


}
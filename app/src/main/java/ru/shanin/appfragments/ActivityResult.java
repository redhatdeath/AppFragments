package ru.shanin.appfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityResult extends AppCompatActivity {

    private static final String ARGUMENT_FROM_INPUT_TEXT = "input_text";
    private String input_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        parseIntent();
        Log.d("ActivityResult", "input_data = " + input_data);
        launchFragment(input_data);
    }

    private void parseIntent() {
        if (!getIntent().hasExtra(ARGUMENT_FROM_INPUT_TEXT))
            throw new RuntimeException("Arguments are absent");
        input_data = getIntent().getStringExtra(ARGUMENT_FROM_INPUT_TEXT);
    }


    public static Intent newIntentActivityResult(Context context, String arg) {
        Intent intent = new Intent(context, ActivityResult.class);
        intent.putExtra(ARGUMENT_FROM_INPUT_TEXT, arg);
        return intent;
    }

    private void launchFragment(String data) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentActivityResult, FragmentAbout.newInstanceWithInputData(data))
                .commitNow();
    }
}
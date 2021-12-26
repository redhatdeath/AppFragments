package ru.shanin.appfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ActivityResult extends AppCompatActivity {

    private static final String ARGUMENT_FROM_INPUT_TEXT = "input_text";
    private String input_data;

    /**
     * Constructor Activity
     * Input data into arg
     *
     * @param arg some input data
     * @return new intent:ActivityResult with input data
     */
    public static Intent newIntentActivityResult(Context context, String arg) {
        Intent intent = new Intent(context, ActivityResult.class);
        intent.putExtra(ARGUMENT_FROM_INPUT_TEXT, arg);
        return intent;
    }

    /**
     * Verify input data
     * If absent -> return RuntimeException
     */

    private void parseIntent() {
        if (!getIntent().hasExtra(ARGUMENT_FROM_INPUT_TEXT))
            throw new RuntimeException("Arguments are absent");
        input_data = getIntent().getStringExtra(ARGUMENT_FROM_INPUT_TEXT);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        parseIntent();
        Log.d("ActivityResult", "input_data = " + input_data);
        if (savedInstanceState == null)
            launchFragmentResult(input_data);

    }

    /**
     * Constructor Fragment
     * Input some data into 'data'
     *
     * @param data some input data. Create new intent:FragmentResult with input data
     */

    private void launchFragmentResult(String data) {
        Fragment fragment;
//        if(bla bla bla){}
//        else {}
        fragment = FragmentResult.newInstanceWithInputData(data);
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
}
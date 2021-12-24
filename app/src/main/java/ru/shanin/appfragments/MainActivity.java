package ru.shanin.appfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button bt;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.bt);
        et = findViewById(R.id.et);

        bt.setOnClickListener(v -> {
            String input = et.getText().toString();
            Intent i = ActivityResult.newIntentActivityResult(getApplicationContext(), input);
            startActivity(i);
        });
    }
}
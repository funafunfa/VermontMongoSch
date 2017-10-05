package com.example.dcamo.vermontmongosch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.dcamo.vermontmongosch.FirstMainActivity.APP_PREFERENCES;
import static com.example.dcamo.vermontmongosch.FirstMainActivity.APP_PREFERENCES_NAME;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences mSettings;
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);



    }


}

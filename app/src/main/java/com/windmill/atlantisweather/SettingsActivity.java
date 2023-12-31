package com.windmill.atlantisweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    private SharedPreferences prefs;
    private EditText edCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Load Settings
        edCity = (EditText) findViewById(R.id.txtCity);
        prefs = getSharedPreferences(IConstansts.PREFS_SETTINGS, Context.MODE_PRIVATE);
        edCity.setText(prefs.getString("hometown", "vienna"));


    }

    @Override
    protected void onPause() {
        super.onPause();

        //Save Settings when leaving
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString("hometown", edCity.getText().toString());
        edit.commit();

    }
}

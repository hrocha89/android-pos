package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.henrique.colecaodiscos.config.Settings;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchMaterial;
    private TextView textTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        switchMaterial = findViewById(R.id.switchTheme);
        textTheme = findViewById(R.id.theme);

        initSwitch();
        switchStattusChecked();

    }

    private void switchStattusChecked() {
        SharedPreferences.Editor edit = getSharedPreferences(Settings.PREFERENCE, MODE_PRIVATE).edit();

        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                edit.putBoolean(Settings.THEME_CUSTOM, true);
                textTheme.setText(R.string.themeDark);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                edit.putBoolean(Settings.THEME_CUSTOM, false);
                textTheme.setText(R.string.themeLigth);
            }
            edit.apply();

        });
    }

    private void initSwitch() {
        SharedPreferences shared = getSharedPreferences(Settings.PREFERENCE, MODE_PRIVATE);
        boolean isNigth = shared.getBoolean(Settings.THEME_CUSTOM, false);

        if (isNigth) {
            switchMaterial.setChecked(true);
            textTheme.setText(R.string.themeDark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            switchMaterial.setChecked(false);
            textTheme.setText(R.string.themeLigth);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cancel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void settings(AppCompatActivity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    private void cancel() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void loadPreferenceTheme() {


    }

    private void switchListener() {

    }

}
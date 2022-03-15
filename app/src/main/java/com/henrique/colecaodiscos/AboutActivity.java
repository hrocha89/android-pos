package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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

    public static void about(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    private void cancel() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
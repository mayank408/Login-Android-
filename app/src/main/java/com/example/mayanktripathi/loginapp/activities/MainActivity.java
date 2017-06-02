package com.example.mayanktripathi.loginapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mayanktripathi.loginapp.R;

/**
 * Created by mayanktripathi on 02/06/17.
 */

public class MainActivity extends AppCompatActivity {

    Button logout;
    private SharedPreferences prefs;
    private static final String ACCESS_TOKEN = "token" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(ACCESS_TOKEN, MODE_PRIVATE);
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", null);
        editor.apply();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

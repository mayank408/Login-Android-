package com.example.mayanktripathi.loginapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayanktripathi.loginapp.R;
import com.example.mayanktripathi.loginapp.model.loginResponse;
import com.example.mayanktripathi.loginapp.rest.ApiClient;
import com.example.mayanktripathi.loginapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String ACCESS_TOKEN = "token" ;
    private EditText login;
    private EditText password;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        prefs = getSharedPreferences(ACCESS_TOKEN, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        Log.d(TAG, "onCreate: token On Login" + token);
        if (token != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        login = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        Button loginButton = (Button)findViewById(R.id.loginbutton);
        Button signUpButton = (Button)findViewById(R.id.sinupbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<loginResponse> call = apiService.doLogin(login.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                String isSuccess = response.body().getSuccess();
                Log.d(TAG, "onResponse: login " + isSuccess);
                Log.d(TAG, "onResponse: token " + response.body().getToken());
                if(isSuccess.equals("true")){
                    pd.cancel();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", response.body().getToken());
                    editor.apply();
                    String token = prefs.getString("token", null);
                    Log.d(TAG, "onCreate: token On response" + token);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    pd.cancel();
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                pd.cancel();
            }
        });

    }


}

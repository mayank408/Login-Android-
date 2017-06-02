package com.example.mayanktripathi.loginapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by mayanktripathi on 02/06/17.
 */

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText login;
    private EditText password;
    private EditText conPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        login = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        conPassword = (EditText)findViewById(R.id.conpassword);
        Button signup = (Button)findViewById(R.id.sinupbutton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();

        if(!password.getText().toString().equals(conPassword.getText().toString())) {
            pd.cancel();
            Toast.makeText(this, "Confirmed password is not same", Toast.LENGTH_SHORT).show();
        }
        else {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<loginResponse> call = apiService.doRegister(login.getText().toString(), password.getText().toString());
            call.enqueue(new Callback<loginResponse>() {
                @Override
                public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                    pd.cancel();
                    String isSuccess = response.body().getSuccess();
                    Log.d(TAG, "onResponse: login" + isSuccess);
                    if(isSuccess.equals("true")){
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<loginResponse> call, Throwable t) {
                    pd.cancel();
                    Log.e(TAG, "onFailure: " + t.toString());
                    Toast.makeText(SignUpActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

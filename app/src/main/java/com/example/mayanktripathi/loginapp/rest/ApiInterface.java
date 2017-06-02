package com.example.mayanktripathi.loginapp.rest;

import com.example.mayanktripathi.loginapp.model.loginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mayanktripathi on 02/06/17.
 */

public interface ApiInterface {

    @POST("login")
    Call<loginResponse> doLogin(@Query("login") String email,
                                @Query("pass") String password);

    @POST("register")
    Call<loginResponse> doRegister(@Query("login") String email,
                                      @Query("pass") String password);

}

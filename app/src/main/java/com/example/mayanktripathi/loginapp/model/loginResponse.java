package com.example.mayanktripathi.loginapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mayanktripathi on 02/06/17.
 */

public class loginResponse {

    @SerializedName("success")
    private String success;

    @SerializedName("token")
    private String token;

    @SerializedName("msg")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

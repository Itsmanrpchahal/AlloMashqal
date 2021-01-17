package com.allomashqal.controller;

import com.allomashqal.Retrofit.WebAPI;
import com.allomashqal.SignIn.response.SignInResponse;
import com.allomashqal.SignUp.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {

    public WebAPI webAPI;
    public SignUpAPI signUpAPI;
    public SignInAPI signInAPI;

    public Controller(SignUpAPI signUp) {
        signUpAPI = signUp;
        webAPI = new WebAPI();
    }

    public Controller(SignInAPI signIn)
    {
        signInAPI = signIn;
        webAPI = new WebAPI();
    }

    public void SetSignUp(String username,String password,String mobile,String device_token,String device_type,String emial)
    {
        webAPI.getApi().signUp(username,password,mobile,device_token,device_type,emial).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful())
                {
                        Response<SignUpResponse> signUpResponseResponse = response;
                                signUpAPI.onSuccessSignUp(signUpResponseResponse);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    signUpAPI.onError(t.getMessage());
            }
        });
    }

    public void SetSignIn(String username,String password,String device_token,String device_type)
    {
        webAPI.getApi().signIn(username, password, device_token, device_type).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.isSuccessful())
                {
                    Response<SignInResponse> signInResponseResponse = response;
                    signInAPI.onSuccessSignIn(signInResponseResponse);
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                    signInAPI.onError(t.getMessage());
            }
        });
    }

  public interface SignUpAPI {
        void onSuccessSignUp(Response<SignUpResponse> success);
        void onError(String error);
    }

    public interface SignInAPI {
        void onSuccessSignIn(Response<SignInResponse> success);
        void onError(String error);
    }
}

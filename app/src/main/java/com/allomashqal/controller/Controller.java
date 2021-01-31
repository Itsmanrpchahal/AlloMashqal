package com.allomashqal.controller;

import com.allomashqal.Retrofit.WebAPI;
import com.allomashqal.SignIn.response.SignInResponse;
import com.allomashqal.SignUp.response.SignUpResponse;
import com.allomashqal.homescreen.fragments.response.GetProfileResponse;
import com.allomashqal.homescreen.response.SalonListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {

    public WebAPI webAPI;
    public SignUpAPI signUpAPI;
    public SignInAPI signInAPI;
    public SalonListAPI salonListAPI;
    public GetProfileAPI getProfileAPI;

    public Controller(SignUpAPI signUp) {
        signUpAPI = signUp;
        webAPI = new WebAPI();
    }

    public Controller(SignInAPI signIn) {
        signInAPI = signIn;
        webAPI = new WebAPI();
    }

    public Controller(SalonListAPI salonList) {
        salonListAPI = salonList;
        webAPI = new WebAPI();
    }

    public Controller (GetProfileAPI getProfile)
    {
        getProfileAPI = getProfile;
        webAPI = new WebAPI();
    }

    public void SetSignUp(String username, String password, String mobile, String device_token, String device_type, String emial) {
        webAPI.getApi().signUp(username, password, mobile, device_token, device_type, emial).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
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

    public void SetSignIn(String username, String password, String device_token, String device_type) {
        webAPI.getApi().signIn(username, password, device_token, device_type).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                Response<SignInResponse> signInResponseResponse = response;
                signInAPI.onSuccessSignIn(signInResponseResponse);
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                signInAPI.onError(t.getMessage());
            }
        });
    }

    public void SalonList(String type, String latitude, String longitude, String page)
    {
        webAPI.getApi().salonList(type, latitude, longitude, page).enqueue(new Callback<SalonListResponse>() {
            @Override
            public void onResponse(Call<SalonListResponse> call, Response<SalonListResponse> response) {
                Response<SalonListResponse> salonListResponseResponse = response;
                salonListAPI.onSuccessSalonList(salonListResponseResponse);
            }

            @Override
            public void onFailure(Call<SalonListResponse> call, Throwable t) {
                    salonListAPI.onError(t.getMessage());
            }
        });
    }

    public void GetProfile(String id)
    {
        webAPI.getApi().getProfile(id).enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                Response<GetProfileResponse> getProfileResponseResponse = response;
                getProfileAPI.onSuccessProfile(getProfileResponseResponse);
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                getProfileAPI.onError(t.getMessage());
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

    public interface SalonListAPI {
        void onSuccessSalonList(Response<SalonListResponse> success);
        void onError(String error);
    }

    public interface GetProfileAPI {
        void onSuccessProfile(Response<GetProfileResponse> success);
        void onError(String error);
    }
}

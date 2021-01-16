package com.allomashqal.Retrofit;

import com.allomashqal.SignUp.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup")
    Call<SignUpResponse> signUp (
            @Field("username") String username,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Field("password") String password
    );
}

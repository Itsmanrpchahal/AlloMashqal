package com.allomashqal.Retrofit;

import com.allomashqal.SignIn.response.SignInResponse;
import com.allomashqal.SignUp.response.SignUpResponse;
import com.allomashqal.homescreen.fragments.response.GetProfileResponse;
import com.allomashqal.homescreen.response.SalonListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("login")
    Call<SignInResponse> signIn (
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type
    );


    @GET("salon")
    Call<SalonListResponse> salonList (
            @Query("type") String type,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("page") String page
    );

    @GET("my-profile")
    Call<GetProfileResponse> getProfile (
      @Query("id") String id
    );
}

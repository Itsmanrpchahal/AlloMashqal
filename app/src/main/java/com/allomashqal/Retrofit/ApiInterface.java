package com.allomashqal.Retrofit;

import com.allomashqal.SignIn.response.SignInResponse;
import com.allomashqal.SignUp.response.SignUpResponse;
import com.allomashqal.chat.response.ChatResponse;
import com.allomashqal.chat.response.SendChatResponse;
import com.allomashqal.homescreen.eventservicesscreen.BookServiceResponse;
import com.allomashqal.homescreen.fragments.response.BookingResponse;
import com.allomashqal.homescreen.fragments.response.GetProfileResponse;
import com.allomashqal.homescreen.fragments.response.NotificationsResponse;
import com.allomashqal.homescreen.fragments.response.UpdateProfileResponse;
import com.allomashqal.homescreen.response.EventServiceDataResponse;
import com.allomashqal.homescreen.response.SalonListResponse;
import com.allomashqal.offers.response.OffersResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup")
    Call<SignUpResponse> signUp(
            @Field("username") String username,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<SignInResponse> signIn(
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type
    );


    @GET("salon")
    Call<SalonListResponse> salonList(
            @Query("type") String type,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("page") String page,
            @Query("search") String search
    );

    @GET("my-profile")
    Call<GetProfileResponse> getProfile(
            @Query("id") String id
    );


    @Multipart
    @POST("update-profile")
    Call<UpdateProfileResponse> updateProfile(
            @Query("id") String id,
            @Query("password") String password,
            @Query("email") String email,
            @Query("phone") String phone,
            @Part MultipartBody.Part image
    );

    @GET("notification")
    Call<NotificationsResponse> notifications(
            @Query("id") String id
    );

    @GET("services")
    Call<EventServiceDataResponse> eventServiceDataResponse(
            @Query("id") String id,
            @Query("userid") String userid
    );

    @FormUrlEncoded
    @POST("make-order")
    Call<BookServiceResponse> bookservice(
            @Field("userid") String userid,
            @Field("providerid") String providerid,
            @Field("serviceid") String serviceid,
            @Field("price") String price,
            @Field("datetime") String datetime,
            @Field("booking_type") String booking_type
    );

    @GET("my-appointments")
    Call<BookingResponse> booking(
            @Query("userid") String userid
    );

    @GET("get-chat")
    Call<ChatResponse> chat(
            @Query("sender_id") String sender_id,
            @Query("reciever_id") String reciever_id
    );

    @FormUrlEncoded
    @POST("send-message")
    Call<SendChatResponse> sendChat(
            @Field("sender_id") String sender_id,
            @Field("reciever_id") String reciever_id,
            @Field("message") String message
    );

    @GET("offers")
    Call<OffersResponse> offers(
            @Query("userid") String userid
    );
}

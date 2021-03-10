package com.allomashqal.controller;

import com.allomashqal.Retrofit.WebAPI;
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

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {

    public WebAPI webAPI;
    public SignUpAPI signUpAPI;
    public SignInAPI signInAPI;
    public SalonListAPI salonListAPI;
    public GetProfileAPI getProfileAPI;
    public UpdateProfileAPI updateProfileAPI;
    public NotificationsAPI notificationsAPI;
    public EventServiceAPI eventServiceAPI;
    public BookServiceAPI bookServiceAPI;
    public BookingAPI bookingAPI;
    public ChatAPI chatAPI;
    public SendChatAPI sendChatAPI;

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

    public Controller (GetProfileAPI getProfile,UpdateProfileAPI updateProfile)
    {
        getProfileAPI = getProfile;
        updateProfileAPI = updateProfile;
        webAPI = new WebAPI();
    }

    public Controller(NotificationsAPI notifications)
    {
        notificationsAPI = notifications;
        webAPI = new WebAPI();
    }

    public Controller(EventServiceAPI eventService,BookServiceAPI bookService)
    {
        eventServiceAPI = eventService;
        bookServiceAPI = bookService;
        webAPI = new WebAPI();
    }

    public Controller(BookingAPI booking)
    {
        bookingAPI = booking;
        webAPI = new WebAPI();
    }

    public Controller(ChatAPI chat,SendChatAPI sendChat)
    {
        chatAPI = chat;
        sendChatAPI = sendChat;
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

    public void updateProfile(String id, String password, String email, String phone, MultipartBody.Part image)
    {
        webAPI.getApi().updateProfile(id, password, email, phone, image).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                Response<UpdateProfileResponse> updateProfileResponseResponse = response;
                updateProfileAPI.onSuccessUpdateProfile(updateProfileResponseResponse);
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                updateProfileAPI.onError(t.getMessage());
            }
        });
    }

    public void Notifications(String id)
    {
        webAPI.getApi().notifications(id).enqueue(new Callback<NotificationsResponse>() {
            @Override
            public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {
                Response<NotificationsResponse> notificationsResponseResponse = response;
                notificationsAPI.onSuccessNotifications(notificationsResponseResponse);
            }

            @Override
            public void onFailure(Call<NotificationsResponse> call, Throwable t) {
                notificationsAPI.onError(t.getMessage());
            }
        });
    }

    public void EventServices(String id,String userid)
    {
        webAPI.getApi().eventServiceDataResponse(id,userid).enqueue(new Callback<EventServiceDataResponse>() {
            @Override
            public void onResponse(Call<EventServiceDataResponse> call, Response<EventServiceDataResponse> response) {
                Response<EventServiceDataResponse> eventServiceDataResponseResponse = response;
                eventServiceAPI.onSuccessEventService(eventServiceDataResponseResponse);
            }

            @Override
            public void onFailure(Call<EventServiceDataResponse> call, Throwable t) {
                eventServiceAPI.onError(t.getMessage());
            }
        });
    }

    public void BookService(String userid,String providerid,String serviceid,String price,String datetime)
    {
        webAPI.getApi().bookservice(userid, providerid, serviceid, price, datetime).enqueue(new Callback<BookServiceResponse>() {
            @Override
            public void onResponse(Call<BookServiceResponse> call, Response<BookServiceResponse> response) {
                Response<BookServiceResponse> bookServiceResponseResponse = response;
                bookServiceAPI.onSuccessBookService(bookServiceResponseResponse);
            }

            @Override
            public void onFailure(Call<BookServiceResponse> call, Throwable t) {
                bookServiceAPI.onError(t.getMessage());
            }
        });
    }

    public void Booking(String userid)
    {
        webAPI.getApi().booking(userid).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                Response<BookingResponse> bookingResponseResponse = response;
                bookingAPI.onSuccessBookoing(bookingResponseResponse);
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                bookingAPI.onError(t.getMessage());
            }
        });
    }

    public void Chat(String senderID,String recieverID)
    {
        webAPI.getApi().chat(senderID, recieverID).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                Response<ChatResponse> chatResponseResponse = response;
                chatAPI.onSuccessChat(chatResponseResponse);
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatAPI.onError(t.getMessage());
            }
        });
    }

    public void SendChat(String senderID,String recieverID,String mesg)
    {
        webAPI.getApi().sendChat(senderID, recieverID, mesg).enqueue(new Callback<SendChatResponse>() {
            @Override
            public void onResponse(Call<SendChatResponse> call, Response<SendChatResponse> response) {
                Response<SendChatResponse> sendChatResponseResponse = response;
                sendChatAPI.onSuccessSendChat(sendChatResponseResponse);
            }

            @Override
            public void onFailure(Call<SendChatResponse> call, Throwable t) {
                sendChatAPI.onError(t.getMessage());
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

    public interface UpdateProfileAPI {
        void onSuccessUpdateProfile(Response<UpdateProfileResponse> success);
        void onError(String error);
    }

    public interface NotificationsAPI {
        void onSuccessNotifications(Response<NotificationsResponse> success);
        void onError(String error);
    }

    public interface EventServiceAPI {
        void onSuccessEventService(Response<EventServiceDataResponse> success);
        void onError(String error);
    }

    public interface BookServiceAPI {
        void onSuccessBookService(Response<BookServiceResponse> success);
        void onError(String error);
    }

    public interface BookingAPI {
        void onSuccessBookoing(Response<BookingResponse> success);
        void onError(String error);
    }

    public interface ChatAPI {
        void onSuccessChat(Response<ChatResponse> success);
        void onError(String error);
    }

    public interface SendChatAPI {
        void onSuccessSendChat(Response<SendChatResponse> success);
        void onError(String error);
    }
}

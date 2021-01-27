package com.example.icem_placement.service;

import com.example.icem_placement.notifications.MyResponse;
import com.example.icem_placement.notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAHX9P-GY:APA91bEurY6aT0Xgts9UY-5ixgKbiVIwtAuphmg9IS2vHlC8uy549VgHXWJNYENzlPmY6N7yk8dgsa7d3zxY6MDsCpSww6-xZHyKxTFOgJaY8_dhZ7Lkkp25j0rTrhWPjyLdC1T_C6kD"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

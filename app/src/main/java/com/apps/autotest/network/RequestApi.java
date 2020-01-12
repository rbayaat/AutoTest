package com.apps.autotest.network;

import com.apps.autotest.model.RideResponse;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestApi {
   @POST("commuter/rides/offers")
    Flowable<RideResponse> getRideDetails(@Body RequestBody body);
}

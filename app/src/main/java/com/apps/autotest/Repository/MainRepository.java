package com.apps.autotest.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.apps.autotest.model.RideResponse;
import com.apps.autotest.network.RequestApi;
import com.google.gson.JsonObject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainRepository {
    private static final String TAG = "MainRepository";
    private static MainRepository instance;
    public static MainRepository getInstance(){
        if(instance == null){
            instance = new MainRepository();
        }
        return instance;
    }

    public LiveData<RideResponse> getRideDetails(RequestApi api, String data){
        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), data);
         return LiveDataReactiveStreams.fromPublisher(api.getRideDetails(body)
                 .onErrorReturn(new Function<Throwable, RideResponse>() {
                     @Override
                     public RideResponse apply(Throwable throwable) throws Exception {
                         Log.e(TAG, "apply: "+throwable.getMessage() );
                         return null;
                     }
                 })
                 .subscribeOn(Schedulers.io()));
    }
}

package com.apps.autotest.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.apps.autotest.utils.Constants;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Named("iran")
    @Provides
    static Typeface provideIranTypeFace(Application application)
    {
        return Typeface.createFromAsset(application.getAssets(),"fonts/font.ttf");
    }
    @Singleton
    @Named("yekan")
    @Provides
    static Typeface provideYekanTypeFace(Application application)
    {
        return Typeface.createFromAsset(application.getAssets(),"fonts/yekan.ttf");
    }
    @Singleton
    @Provides
    static Gson provideGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }
    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(Gson gson)
    {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    @Singleton
    @Provides
    static Picasso providePicasso(Application application)
    {
        return Picasso.with(application);
    }

}

package com.apps.autotest.di.main;

import com.apps.autotest.network.RequestApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @Provides
    static RequestApi provideAuthApi(Retrofit retrofit)
    {
        return retrofit.create(RequestApi.class);
    }

}

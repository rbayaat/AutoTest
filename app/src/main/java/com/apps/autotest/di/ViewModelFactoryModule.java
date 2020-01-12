package com.apps.autotest.di;

import androidx.lifecycle.ViewModelProvider;
import com.apps.autotest.utils.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);

}

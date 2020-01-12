package com.apps.autotest.di.main;

import androidx.lifecycle.ViewModel;
import com.apps.autotest.di.ViewModelKey;
import com.apps.autotest.ui.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}

package com.apps.autotest.di.detail;

import androidx.lifecycle.ViewModel;

import com.apps.autotest.di.ViewModelKey;
import com.apps.autotest.ui.detail.DetailViewModel;
import com.apps.autotest.ui.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class DetailViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    public abstract ViewModel bindDetailViewModel(DetailViewModel viewModel);
}

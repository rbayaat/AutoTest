package com.apps.autotest.di;
import com.apps.autotest.di.detail.DetailViewModelsModule;
import com.apps.autotest.di.main.MainModule;
import com.apps.autotest.di.main.MainViewModelsModule;
import com.apps.autotest.ui.detail.DetailActivity;
import com.apps.autotest.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class  ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    MainModule.class,
                    MainViewModelsModule.class
            }
    )
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(
            modules = {
                    DetailViewModelsModule.class
            }
    )
    abstract DetailActivity contributeDetailActivity();
}

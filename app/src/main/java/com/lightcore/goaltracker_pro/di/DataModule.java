package com.lightcore.goaltracker_pro.di;

import com.lightcore.goaltracker_pro.DataSource.GetFirebase;
import com.lightcore.goaltracker_pro.DoMain.GetDataFrbs;
import com.lightcore.goaltracker_pro.DoMain.GetFirebaseInterface;
import com.lightcore.goaltracker_pro.ui.onlTasx.SlideshowViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DataModule {
//    @Provides
//    public GetFirebase provideGetFirebase(){return new GetFirebase();}
@Provides
@Singleton
public GetFirebaseInterface provideGetFirebaseInterface() {return new GetFirebase();}
    @Provides
    @Singleton
    public GetDataFrbs provideGetDataFrbs(GetFirebaseInterface getFirebaseInterface){return new GetDataFrbs(getFirebaseInterface);}
    @Provides
    @Singleton
    public SlideshowViewModel provideSViewModel(GetDataFrbs getDataFrbs){return new SlideshowViewModel(getDataFrbs);}
}

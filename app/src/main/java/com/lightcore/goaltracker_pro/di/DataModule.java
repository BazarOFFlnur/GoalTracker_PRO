package com.lightcore.goaltracker_pro.di;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DataSource.GetFirebase;
import com.lightcore.goaltracker_pro.DataSource.SetFirebase;
import com.lightcore.goaltracker_pro.DataSource.UpdateFirebase;
import com.lightcore.goaltracker_pro.DoMain.CompleteTask;
import com.lightcore.goaltracker_pro.DoMain.GetDataFrbs;
import com.lightcore.goaltracker_pro.DoMain.SetDataFirebase;
import com.lightcore.goaltracker_pro.ui.testr.Sec;
import com.lightcore.goaltracker_pro.ui.testr.Tsd;
//import com.lightcore.goaltracker_pro.DataSource.GetFirebase;
//import com.lightcore.goaltracker_pro.DoMain.GetDataFrbs;
//import com.lightcore.goaltracker_pro.DoMain.GetFirebaseInterface;

import org.checkerframework.checker.units.qual.C;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    public Tsd provideTsd(Sec sec){
        return new Tsd(sec);
    }
    @Provides
    public Sec provideSec(){
        return new Sec();
    }

    @Provides
    public GetDataFrbs provideGetDataFrbs(GetFirebase getFirebase) {
        return new GetDataFrbs(getFirebase);

    }
    @Provides
    public GetFirebase provdeGetFirebase() {
        return new GetFirebase();
    }

    @Provides
    public SetDataFirebase provideSetDataFirebase(SetFirebase setFirebase){
        return new SetDataFirebase(setFirebase);
    }
    @Provides
    public SetFirebase provideSetFirebase(){
        return new SetFirebase();
    }
    @Provides
    public CompleteTask provideCompleteTasl(UpdateFirebase updateFirebase){return new CompleteTask(updateFirebase);}
    @Provides
    public UpdateFirebase provideUpdateFirebase(){return new UpdateFirebase();}
}


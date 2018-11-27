package com.accenture.kartik.accenturetask.di.module;

import android.content.Context;
import android.os.Bundle;

import com.accenture.kartik.accenturetask.db.DatabaseManager;
import com.accenture.kartik.accenturetask.rest.AccentureRepository;
import com.accenture.kartik.accenturetask.usecase.GetAlbumUseCase;
import com.accenture.kartik.accenturetask.views.AlbumListAdapter;
import com.accenture.kartik.accenturetask.views.activities.MainActivity;
import com.accenture.kartik.accenturetask.di.scopes.PerActivity;
import com.accenture.kartik.accenturetask.mvp.model.MainModel;
import com.accenture.kartik.accenturetask.mvp.presenter.MainPresenter;

import org.parceler.Parcels;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class MainModule {

    private final Bundle mSavedInstanceState;

    public MainModule(Bundle savedInstanceState) {

        mSavedInstanceState = savedInstanceState;
    }

    @Provides
    @PerActivity
    MainPresenter provideMainPresenter(Context context,
                                       MainModel model,
                                       @Named("rest_use_case") GetAlbumUseCase getAlbumUseCase,
                                       DatabaseManager databaseManager) {

        MainPresenter mainPresenter = new MainPresenter(
                context,
                model,
                getAlbumUseCase,
                databaseManager);

        return mainPresenter;
    }

    @Provides
    @PerActivity
    MainModel provideMainModel() {

        MainModel model;
        if (mSavedInstanceState == null) {
            model = new MainModel();
        } else {
            model = Parcels.unwrap(mSavedInstanceState.getParcelable(MainActivity.STATE_MAIN_MODEL));
        }

        return model;
    }

    @Provides
    @PerActivity
    AlbumListAdapter provideAlbumAdapter(Context context, MainModel model) {

        return new AlbumListAdapter(context, model.getAlbums());
    }

    @Provides
    @PerActivity
    @Named("rest_use_case")
    GetAlbumUseCase provideGetAlbumUseCase(@Named("rest_repository") AccentureRepository repository,
                                                  @Named("ui_thread") Scheduler uiThread,
                                                  @Named("executor_thread") Scheduler executorThread) {

        return new GetAlbumUseCase(repository, uiThread, executorThread);
    }

    /**
     * Provides the database data source.
     *
     * @return The database data source.
     */
    @Provides
    @PerActivity
    DatabaseManager provideDatabaseManager(Context context) {

        return new DatabaseManager(context);
    }

}
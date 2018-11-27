package com.accenture.kartik.accenturetask.di.component;

import com.accenture.kartik.accenturetask.AccentureApplication;
import com.accenture.kartik.accenturetask.di.module.AppModule;
import com.accenture.kartik.accenturetask.di.scopes.PerApplication;
import com.accenture.kartik.accenturetask.rest.AccentureRepository;

import javax.inject.Named;

import dagger.Component;
import rx.Scheduler;


@PerApplication
@Component( modules = AppModule.class )
public interface AppComponent{

    /**
     * Exposes the app object.
     *
     * @return The app object.
     */
    AccentureApplication app();

    /**
     * Exposes the executor thread scheduler.
     *
     * @return The executor thread scheduler.
     */
    @Named("executor_thread") Scheduler executorThread();

    /**
     * Exposes the ui thread scheduler.
     *
     * @return The ui thread scheduler.
     */
    @Named("ui_thread") Scheduler uiThread();

    /**
     * Exposes the rest api repository.
     * @return The rest api repository.
     */
    @Named("rest_repository")
    AccentureRepository restRepo();


//    /**
//     * Exposes the database data source.
//     *
//     * @return The database data source.
//     */
//    DatabaseDataSource databaseDataSource();

}

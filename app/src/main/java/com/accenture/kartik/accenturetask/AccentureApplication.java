package com.accenture.kartik.accenturetask;

import android.app.Application;
import android.content.Context;

import com.accenture.kartik.accenturetask.di.component.AppComponent;
import com.accenture.kartik.accenturetask.di.component.DaggerAppComponent;
import com.accenture.kartik.accenturetask.di.module.AppModule;

public class AccentureApplication extends Application {

    /**
     * Static context.
     */
    private static Context sContext;

    /**
     * The app component.
     */
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initContext();
        initDependencyInjector();
    }

    /**
     * Returns the app component used for initializing
     * the activity dependency injectors.
     *
     * @return The app component.
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * Initializes the dependency injector.
     */
    protected void initDependencyInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initContext() {
        sContext = this;
    }

}

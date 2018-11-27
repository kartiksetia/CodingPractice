package com.accenture.kartik.accenturetask.di.component;

import com.accenture.kartik.accenturetask.di.module.ActivityModule;
import com.accenture.kartik.accenturetask.di.module.MainModule;
import com.accenture.kartik.accenturetask.di.scopes.PerActivity;
import com.accenture.kartik.accenturetask.views.activities.MainActivity;

import dagger.Component;

@PerActivity
@Component( dependencies = {AppComponent.class}, modules = {MainModule.class, ActivityModule.class} )
public interface MainComponent extends ActivityComponent{

    /**
     * Injects the component in the {@code MainActivity}.
     *
     * @param mainActivity The {@code MainActivity}.
     */
    void inject(MainActivity mainActivity);
}

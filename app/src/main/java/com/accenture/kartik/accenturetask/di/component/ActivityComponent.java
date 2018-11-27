package com.accenture.kartik.accenturetask.di.component;

import android.content.Context;

import com.accenture.kartik.accenturetask.di.module.ActivityModule;
import com.accenture.kartik.accenturetask.di.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    /**
     * Exposes the activity context.
     *
     * @return The activity context.
     */
    Context context();
}

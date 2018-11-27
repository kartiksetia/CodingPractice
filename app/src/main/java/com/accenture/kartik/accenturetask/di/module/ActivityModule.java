package com.accenture.kartik.accenturetask.di.module;

import android.content.Context;

import com.accenture.kartik.accenturetask.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    /**
     * The activity context.
     */
    private final Context mContext;

    /**
     * Constructor.
     *
     * @param mContext The activity context.
     */
    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    /**
     * Provides the activity context.
     *
     * @return The activity context.
     */
    @Provides
    @PerActivity
    Context provideActivityContext() {

        return mContext;
    }
}

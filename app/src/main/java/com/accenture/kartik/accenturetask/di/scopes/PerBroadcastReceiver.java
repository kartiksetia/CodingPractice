package com.accenture.kartik.accenturetask.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface PerBroadcastReceiver {
}

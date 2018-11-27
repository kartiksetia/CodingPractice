package com.accenture.kartik.accenturetask.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        ButterKnife.bind(this);
    }


    protected abstract int getActivityLayout();
}

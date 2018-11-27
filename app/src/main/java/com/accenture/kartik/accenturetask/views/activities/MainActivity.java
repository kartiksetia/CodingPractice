package com.accenture.kartik.accenturetask.views.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.accenture.kartik.accenturetask.AccentureApplication;
import com.accenture.kartik.accenturetask.R;
import com.accenture.kartik.accenturetask.di.component.DaggerMainComponent;
import com.accenture.kartik.accenturetask.di.component.MainComponent;
import com.accenture.kartik.accenturetask.di.module.ActivityModule;
import com.accenture.kartik.accenturetask.di.module.MainModule;
import com.accenture.kartik.accenturetask.mvp.presenter.MainPresenter;
import com.accenture.kartik.accenturetask.mvp.view.MainView;
import com.accenture.kartik.accenturetask.views.AlbumListAdapter;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {

    /**
     * The state of the model key.
     */
    public static final String STATE_MAIN_MODEL = "STATE_MAIN_MODEL";

    @Inject MainPresenter mPresenter;
    @Inject AlbumListAdapter albumListAdapter;
    @BindView(R.id.listView)
    ListView mListViewAlbum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDependencyInjector(savedInstanceState);
        initPresenter();
        initListView();
        mPresenter.onCreate();
    }



    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    /**
     * Initializes the dependency injector.
     *
     * @param savedInstanceState The saved instance state.
     */
    private void initDependencyInjector(Bundle savedInstanceState) {

        AccentureApplication captureApplication = (AccentureApplication) getApplication();

        MainComponent mainComponent = DaggerMainComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(captureApplication.getAppComponent())
                .mainModule(new MainModule(savedInstanceState))
                .build();


        mainComponent.inject(this);
    }

    private void initPresenter() {

        mPresenter.bindView(this);
    }

    private void initListView() {
        mListViewAlbum.setAdapter(albumListAdapter);
    }

    @Override
    public void showErrorDialog(String title, String message) {

    }

    @Override
    public void updateList() {
        albumListAdapter.notifyDataSetChanged();
        mListViewAlbum.setAdapter(albumListAdapter);
    }
}

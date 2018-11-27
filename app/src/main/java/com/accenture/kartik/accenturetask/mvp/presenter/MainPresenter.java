package com.accenture.kartik.accenturetask.mvp.presenter;

import android.content.Context;
import android.widget.Toast;

import com.accenture.kartik.accenturetask.Utils.NetworkUtils;
import com.accenture.kartik.accenturetask.db.DatabaseManager;
import com.accenture.kartik.accenturetask.domain.Album;
import com.accenture.kartik.accenturetask.mvp.model.MainModel;
import com.accenture.kartik.accenturetask.mvp.view.MainView;
import com.accenture.kartik.accenturetask.usecase.GetAlbumUseCase;
import com.accenture.kartik.accenturetask.views.activities.MainActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class MainPresenter extends BasePresenter<MainModel, MainView> {


    private GetAlbumUseCase mGetAlbumUseCase;

    /**
     * The database data source.
     */
    private DatabaseManager mDatabaseDataSource;


    /**
     * The rx subscriber.
     */
    private Subscriber mSubscriber;

    private Context mContext;

    /**
     * Constructor.
     *
     * @param context The context.
     * @param model The model of the mvp architecture.
     */
    public MainPresenter(Context context,
                         MainModel model,
                         GetAlbumUseCase getAlbumUseCase,
                         DatabaseManager databaseDataSource) {
        mContext = context;
        mModel = model;
        mGetAlbumUseCase = getAlbumUseCase;
        mDatabaseDataSource = databaseDataSource;
    }

    @Override
    protected void updateView() {
        view().updateList();

    }

    @Override
    public void onCreate() {
        mDatabaseDataSource.open();
        //check if the database already exist in the app
        if(isDataBaseExist()){
            loadAlbumsFromDB();
        }else {
            loadAlbums();
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        mDatabaseDataSource.open();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mDatabaseDataSource.close();
    }

    private void loadAlbums() {
        initSubscriber();
        if(NetworkUtils.isNetworkAvailable(mContext)){
        executeRestUseCase().subscribe(mSubscriber);
        }else {
            Toast.makeText(mContext, "No Network Connection Please try again later",
                    Toast.LENGTH_LONG).show();
        }
    }

    private Observable<List<Album>> executeRestUseCase() {

        return mGetAlbumUseCase.execute();
    }

    private boolean isDataBaseExist(){
        return mDatabaseDataSource.isExist();
    }

    private void loadAlbumsFromDB() {
        List<Album> albums = mDatabaseDataSource.queryAllAlbums();
        if (albums != null) {
            mModel.setAlbums(albums);
        }
        updateView();
    }


    /**
     * Initializes the rx subscriber.
     */
    private void initSubscriber() {

        unsubscribeSubscriber();

        if (mSubscriber == null) {
            mSubscriber = createSubscriber();
        }
    }

    /**
     * Creates the rx subscriber.
     * @return The new rx subscriber.
     */
    private Subscriber createSubscriber() {

        return new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                e.printStackTrace();
                Toast.makeText(mContext, "Some Error ! Please try again later",
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNext(List<Album> list) {
                mModel.setAlbums(list);
                for(Album album : list){
                   mDatabaseDataSource.storeAlbum(album);
                }
                updateView();
            }
        };
    }

    /**
     * Unsubscribes the rx subscriber.
     */
    private void unsubscribeSubscriber() {

        if (mSubscriber != null) {
            mSubscriber.unsubscribe();
            mSubscriber = null;
        }
    }
}

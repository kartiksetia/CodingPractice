package com.accenture.kartik.accenturetask.di.module;

import com.accenture.kartik.accenturetask.AccentureApplication;
import com.accenture.kartik.accenturetask.BuildConfig;
import com.accenture.kartik.accenturetask.db.DatabaseManager;
import com.accenture.kartik.accenturetask.di.scopes.PerApplication;
import com.accenture.kartik.accenturetask.rest.AccentureApi;
import com.accenture.kartik.accenturetask.rest.AccentureRepository;
import com.accenture.kartik.accenturetask.rest.Endpoint;
import com.accenture.kartik.accenturetask.rest.RestDataSource;
import com.accenture.kartik.accenturetask.rest.SuperclassExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    /**
     * The application object.
     */
    private final AccentureApplication mApplication;

    /**
     * Constructor.
     *
     * @param application The application object.
     */
    public AppModule(AccentureApplication application){

        mApplication = application;
    }

    /**
     * Provides the application object.
     *
     * @return The application object.
     */
    @Provides
    @PerApplication
    AccentureApplication providesApplication(){

        return mApplication;
    }



    /**
     * Provides the endpoint url taken from the build config.
     *
     * @return The endpoint url.
     */
    @Provides
    @PerApplication
    Endpoint provideEndpoint() {

        Endpoint endpoint = new Endpoint(BuildConfig.ENDPOINT);
        return endpoint;
    }

    /**
     * Provides the network logging interceptor.
     *
     * @return The network logging interceptor.
     */
    @Provides
    @PerApplication
    HttpLoggingInterceptor provideLoggingInterceptor() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else{
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
    }


    /**
     * Provides the http cache.
     *
     * @param application The application object.
     * @return The http cache.
     */
    @Provides
    @PerApplication
    Cache provideOkHttpCache(AccentureApplication application) {

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }


    /**
     * Provides the okhttp client used internally in retrofit. This client is the
     * base for any other okhttp clients used for different apis, so the
     * internal thread pools and resources are shared.
     *
     * @return The okhttp client.
     */
    @Provides
    @PerApplication
    @Named("base_ok_http_client")
    OkHttpClient provideOkHttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return okHttpClient;
    }

    /**
     * Provides the okhttp client used for accenture api.
     *
     * @param baseOkHttpClient The base okhttp client.
     * @param cache The http cache.
     * @param loggingInterceptor Interceptor logging network requests.
     * @return The okhttp client used for the accenture api.
     */
    @Provides
    @PerApplication
    @Named("accenture_api_ok_http_client")
    OkHttpClient provideaccentureApiOkHttpClient(@Named("base_ok_http_client") OkHttpClient baseOkHttpClient,
                                                 Cache cache,
                                                 HttpLoggingInterceptor loggingInterceptor) {

        OkHttpClient accentureApiOkHttpClient = baseOkHttpClient.newBuilder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return accentureApiOkHttpClient;
    }


    /**
     * Provides the gson converter factory. It is used internally in retrofit to convert objects
     * from json.
     *
     * @return The gson converter factory.
     */
    @Provides
    @PerApplication
    GsonConverterFactory provideGsonConverterFactory() {

        Gson customGsonInstance = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .excludeFieldsWithoutExposeAnnotation()
                .addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
                .addSerializationExclusionStrategy(new SuperclassExclusionStrategy())
                .serializeNulls()
                .create();

        return GsonConverterFactory.create(customGsonInstance);
    }

    /**
     * Provides the rx java adapter factory. It is used internally in retrofit to support
     * rx java calls instead of regular callbacks.
     *
     * @return The rx java adapter factory.
     */
    @Provides
    @PerApplication
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {

        return RxJavaCallAdapterFactory.create();
    }

    /**
     * Provides the accenture retrofit instance.
     *
     * @param okHttpClient The okhttp client.
     * @param endpoint The backend endpoint.
     * @param gsonConverterFactory The gson converter factory.
     * @param rxJavaCallAdapterFactory The rx java adapter factory.
     * @return The accenture retrofit instance.
     */
    @Provides
    @PerApplication
    @Named("accenture_api_retrofit")
    Retrofit provideaccentureApiRetrofit(@Named("accenture_api_ok_http_client") OkHttpClient okHttpClient,
                                         Endpoint endpoint,
                                         GsonConverterFactory gsonConverterFactory,
                                         RxJavaCallAdapterFactory rxJavaCallAdapterFactory) {

        Retrofit accentureApiRetrofit = new Retrofit.Builder()
                .baseUrl(endpoint.getEndpoint())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .build();

        return accentureApiRetrofit;
    }



    /**
     * Provides the accenture api.
     *
     * @param retrofit The accenture retrofit instance.
     * @return The accenture api.
     */
    @Provides
    @PerApplication
    AccentureApi provideaccentureApi(@Named("accenture_api_retrofit") Retrofit retrofit) {

        return retrofit.create(AccentureApi.class);
    }



    /**
     * Provides the rest data source.
     *
     * @param accentureApi The accenture api.
     * @return The rest data source.
     */
    @Provides
    @PerApplication
    RestDataSource provideRestDataSource(AccentureApi accentureApi) {

        return new RestDataSource(accentureApi);
    }


    /**
     * Provides the rest repository.
     *
     * @param restDataSource The rest data source.
     * @return The rest repository.
     */
    @Provides
    @PerApplication
    @Named("rest_repository")
    AccentureRepository provideRestDataSourceRepository(RestDataSource restDataSource) {

        return restDataSource;
    }


    /**
     * Provides a new executor thread. Needed for the rx.
     *
     * @return A new executor thread.
     */
    @Provides
    @Named("executor_thread")
    Scheduler provideExecutorThread() {

        return Schedulers.newThread();
    }

    /**
     * Provides the UI thread. Needed for the rx subscription.
     *
     * @return The UI thread.
     */
    @Provides
    @Named("ui_thread")
    Scheduler provideUiThread() {

        return AndroidSchedulers.mainThread();
    }



}


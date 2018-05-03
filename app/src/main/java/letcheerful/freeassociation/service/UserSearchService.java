package letcheerful.freeassociation.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import letcheerful.freeassociation.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserSearchService {

    private UserSearchAPI api;

    public UserSearchService(String serverAddress) {
        api = (UserSearchAPI) createAPI(UserSearchAPI.class, serverAddress);
    }

    private Object createAPI(Class<?> apiClass, String serverAddress) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(logger)
                .connectTimeout(0, TimeUnit.HOURS)
                .readTimeout(0, TimeUnit.HOURS)
                .writeTimeout(0, TimeUnit.HOURS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(serverAddress)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build().create(apiClass);
    }

    public UserSearchAPI getApi() {
        return api;
    }
}

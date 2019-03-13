package cn.ifhu.net;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIManager {

    private static final long TIMEOUT = 30;
    private static Retrofit retrofit;
    private static Retrofit serializeNullsRetrofit;
    private static Retrofit uploadRetrofit;
    private static Retrofit dashBoardRetrofit;

    private RetrofitAPIManager() {

    }

    public static Retrofit getLinkClientApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getLinkClientApiWithNull() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (serializeNullsRetrofit == null) {
            serializeNullsRetrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(genericClient())
                    .build();
        }

        return serializeNullsRetrofit;
    }

    /**
     * 上传专用
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T createUpload(final Class<T> service) {
        return getLinkUploadClientApi().create(service);
    }


    public static <T> T create(final Class<T> service) {
        return getLinkClientApi().create(service);
    }

    /**
     * dashboard 专用api
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T createDashboard(final Class<T> service) {
        return getDashBoardClientApi().create(service);
    }

    public static <T> T createSerializeNulls(final Class<T> service) {
        return getLinkClientApiWithNull().create(service);
    }

    /**
     * dashboard专用的Retrofit
     */
    public static Retrofit getDashBoardClientApi() {
        if (dashBoardRetrofit == null) {
            dashBoardRetrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient())
                    .build();
        }
        return dashBoardRetrofit;
    }

    /**
     * 上传图片专用的Retrofit
     */
    public static Retrofit getLinkUploadClientApi() {
        if (uploadRetrofit == null) {
            uploadRetrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericUploadClient())
                    .build();
        }

        return uploadRetrofit;
    }

    private static OkHttpClient genericClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = "";
                String dataToken = "";
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("88-device-id", "")
                        .addHeader("User-Agent", "")
                        .addHeader("88-token", token)
                        .addHeader("88-jwt", dataToken)
                        .addHeader("88-app-version", "")
                        .build();
                return chain.proceed(request);
            }

        });
        OkHttpClient httpClient = builder.build();
        return httpClient;
    }




    private static OkHttpClient genericUploadClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = "";
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("88-device-id", "")
                                .addHeader("User-Agent", "")
                                .addHeader("88-token", token)
                                .build();
                        return chain.proceed(request);
                    }

                })
//                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return httpClient;
    }
}

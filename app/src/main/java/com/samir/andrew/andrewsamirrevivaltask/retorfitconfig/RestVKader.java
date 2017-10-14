package com.samir.andrew.andrewsamirrevivaltask.retorfitconfig;

import android.content.Context;
import android.content.pm.PackageManager;


import com.samir.andrew.andrewsamirrevivaltask.utilities.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestVKader {

    private static RestVKader instance;
    private static ApiCall apiService;
    //public final String BASE_URL = "";
    private static Context mcontext;
    // public String apiValue = "";
    public String apiKey = "ApiKey";
    public String Authorization = "Authorization";

    private RestVKader() {


//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();


        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(6, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES);

// comment start to create signed apk
/*
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
*/
// comment end
        //  httpClient.addInterceptor(interceptor).build();


        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Request newRequest;
                newRequest = request.newBuilder()
                        //can add header here
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(newRequest);
            }
        });

        OkHttpClient httpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        apiService = retrofit.create(ApiCall.class);
    }

    public static RestVKader getInstance(Context context) {
        mcontext = context;
        if (instance == null) {
            instance = new RestVKader();
        }
        return instance;
    }

    // get current version code to ask him to update the application if there is new update
    public static String getVersionCode(Context c) {
        String v = "";
        try {
            v += c.getPackageManager()
                    .getPackageInfo(c.getPackageName(), 0).versionName;
            // SharedPrefUtil.getInstance(mcontext).write(DataEnum.shversionName.name(), v);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // Log.e("log",v);
        return v;
    }

    public ApiCall getClientService() {
        return apiService;
    }
}
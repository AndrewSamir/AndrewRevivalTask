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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// start comment
// end comment

// import okhttp3.logging.HttpLoggingInterceptor;

public class RestVKader {

    private static RestVKader instance;
    private static ApiCall apiService;
    //public final String BASE_URL = "http://192.168.1.222/";
    private static Context mcontext;
    // public String apiValue = "9c4a06e4dddceb70722de4f3fda4f2c7";
    public String apiKey = "ApiKey";
    public String Authorization = "Authorization";

    private RestVKader() {


//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();


        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(6, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES);
//coment start

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

//comment end
        //  httpClient.addInterceptor(interceptor).build();


        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Request newRequest;
              //  String apiValue = Constant.getInstance(mcontext).apiValue;
                //String token = SharedPrefUtil.getInstance(mcontext).read(DataEnum.shAuthToken.name(), "");

                //  String firebasetoken = "";

            //    String firebasetoken = SharedPrefUtil.getInstance(mcontext).read(DataEnum.shFirebaseToken.name(), "");
              //  Log.e("log", token + "==" + firebasetoken);

             /*   if (SharedPrefUtil.getInstance(mcontext).read(DataEnum.shIsFirebaseCall.name(), false)) {
                    SharedPrefUtil.getInstance(mcontext).write(DataEnum.shIsFirebaseCall.name(), false);
                    newRequest = request.newBuilder()

                            .header("Content-Type", "application/json")
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(newRequest);
                } else if (!token.isEmpty() && !firebasetoken.isEmpty()) {
                    newRequest = request.newBuilder()


                            .header(apiKey, apiValue)

                            .header("Authorization", token)
                            .header("device", firebasetoken)
                            .header("version", getVestionCode(mcontext))
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(newRequest);
                } else if (!token.isEmpty()) {
                    newRequest = request.newBuilder()
                            .header(apiKey, apiValue)

                            .header("Authorization", token)
                            // .header("device", SharedPrefUtil.getInstance(mcontext).read("firebasetoken", ""))
                            .header("version", getVestionCode(mcontext))
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(newRequest);
                } else if (!firebasetoken.isEmpty()) {
                    newRequest = request.newBuilder()
                            .header(apiKey, apiValue)
                            //.header("Authorization", SharedPrefUtil.getInstance(mcontext).read("auth_token", ""))
                            .header("device", firebasetoken)

                            .header("version", getVestionCode(mcontext))
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(newRequest);
                } else {*/
                    newRequest = request.newBuilder()


                           // .header(apiKey, apiValue)

                            // .header("Authorization", SharedPrefUtil.getInstance(mcontext).read("auth_token", ""))
                            //.header("device", SharedPrefUtil.getInstance(mcontext).read("firebasetoken", ""))
                           // .header("version", getVestionCode(mcontext))
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(newRequest);
               // }

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

    public static String getVestionCode(Context c) {
        /*
        p40sdmkkmgjb1ggyadqz
        e4bbe5b7a4c1eb55652965aee885dd59bd2ee7f4
         */
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
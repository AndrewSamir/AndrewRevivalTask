package com.samir.andrew.andrewsamirrevivaltask.retorfitconfig;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.samir.andrew.andrewsamirrevivaltask.R;
import com.samir.andrew.andrewsamirrevivaltask.googlePlacesApis.ModelGooglePlacesApis;
import com.samir.andrew.andrewsamirrevivaltask.interfaces.HandleRetrofitResp;
import com.sdsmdg.tastytoast.TastyToast;

import developer.mokadim.projectmate.dialog.IndicatorStyle;
import developer.mokadim.projectmate.dialog.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by lenovo on 6/28/2017.
 */

public class HandleCalls {
    private static Context context;
    private static HandleCalls instance = null;
    private static RestVKader restVKader;
    private HandleRetrofitResp onRespnse;

    public static HandleCalls getInstance(Context context) {

        HandleCalls.context = context;

        if (instance == null) {
            instance = new HandleCalls();
            restVKader = RestVKader.getInstance(context);
        }
        return instance;
    }

    public void setonRespnseSucess(HandleRetrofitResp onRespnseSucess) {
        this.onRespnse = onRespnseSucess;
    }

    public void callGetGooglePlaces(final String flag, String location, String radius) {

        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();
        Call<ModelGooglePlacesApis> call = restVKader.getClientService().getGooglePlacesCall(location, radius, "AIzaSyC2-JH7QqoaikPj8KWV1x4vMzoDrqPFFT0");

        call.enqueue(new Callback<ModelGooglePlacesApis>() {
            @Override
            public void onResponse(Call<ModelGooglePlacesApis> call, Response<ModelGooglePlacesApis> response) {

                if (response.code() == 200) {
                    if (response.isSuccessful() && response.body() != null) {
                        ModelGooglePlacesApis resp = response.body();

                        if (resp.getStatus().equals("OK")) {
                            // Log.e("callsucess", "sucess");
                            if (onRespnse != null)
                                onRespnse.onResponseSuccess(flag, resp);

                        } else {
                            TastyToast.makeText(context, context.getString(R.string.internet_problem), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        }
                        // Model is Ready
                        progressDialog.dismiss();
                    }
                } else {
                    TastyToast.makeText(context, context.getString(R.string.server_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelGooglePlacesApis> call, Throwable t) {
                //  Log.d("error", t.getMessage());
                TastyToast.makeText(context, context.getString(R.string.server_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                progressDialog.dismiss();
            }
        });
    }
}
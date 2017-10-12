package com.samir.andrew.andrewsamirrevivaltask.interfaces;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface HandleRetrofitResp {
    // id is selected id from dialog
    // name is selected name
    // flag witch dialog clciked
    void onResponseSuccess(String flag, Object o);

    void onNoContent(String flag, int code);

    void onResponseSuccess(String flag, Object o, int position);

}

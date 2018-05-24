package ntamtech.adinz.utils;

import android.app.Application;

import ntamtech.adinz.api.ApiConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiConfig.initRetrofitConfig();
    }
}

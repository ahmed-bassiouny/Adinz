package ntamtech.adinz.utils;

import android.app.Application;

import bassiouny.ahmed.genericmanager.SharedPrefManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import ntamtech.adinz.R;
import ntamtech.adinz.api.ApiConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ApiConfig.initRetrofitConfig();
        SharedPrefManager.init(this,getString(R.string.app_name));
        Realm.init(this);
    }
}

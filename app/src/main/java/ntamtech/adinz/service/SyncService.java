package ntamtech.adinz.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;

public class SyncService extends IntentService {


    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("onHandleIntent:", "onHandleIntent: " );
        /*int i = 0;
        DataBaseOperation dataBaseOperation = new DataBaseOperation();
        List<AdModel> ads= dataBaseOperation.getAllAds();
        int adSize = ads.size();
        AdModel adModel = ads.get(i);
        */
        Log.e("onHandleIntent:", "onHandleIntent: " );
    }
}

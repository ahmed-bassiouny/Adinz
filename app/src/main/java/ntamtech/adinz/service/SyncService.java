package ntamtech.adinz.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import ntamtech.adinz.api.ApiRequests;
import ntamtech.adinz.api.apiModel.requests.AdsViewRequest;
import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.BaseResponseInterface;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.utils.MyUtils;

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

        DataBaseOperation dataBaseOperation = new DataBaseOperation();
        AdsViewRequest adsViewRequest = new AdsViewRequest(
                MyUtils.getCurrentDateTime(),
                dataBaseOperation.getAllDriverAdModel());
        ApiRequests.syncAds(adsViewRequest, new BaseResponseInterface<List<AdModel>>() {
            @Override
            public void onSuccess(List<AdModel> adModels) {
                // todo save new ads
                // todo remove driver ads
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }
}

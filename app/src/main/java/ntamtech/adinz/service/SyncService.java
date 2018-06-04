package ntamtech.adinz.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ntamtech.adinz.api.ApiRequests;
import ntamtech.adinz.api.apiModel.requests.AdsViewRequest;
import ntamtech.adinz.api.apiModel.requests.DriverAdRequest;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.BaseResponseInterface;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.model.DriverAdModel;
import ntamtech.adinz.utils.Constant;
import ntamtech.adinz.utils.MyUtils;
import ntamtech.adinz.view.HomeActivity;

public class SyncService extends Service {

    private DataBaseOperation dataBaseOperation;
    public static final int SYNC_PER_COUNT = 2;
    private HomeController controller;
    private int iteration = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        waitToSync();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void waitToSync() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(5000);
                            sync();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    private void sync() {
        dataBaseOperation = new DataBaseOperation();
        if(dataBaseOperation.getDriverAdModelSize() < SYNC_PER_COUNT)
            return;
        final List<DriverAdModel> driverAdModels = dataBaseOperation.getAllDriverAdModelLimit();
        List<DriverAdRequest> driverAdRequests = new ArrayList<>();
        for(DriverAdModel item : driverAdModels){
            driverAdRequests.add(new DriverAdRequest(item.getAdvertisementId(),item.getDriverId(),item.getLatitude(),
                    item.getLongitude(),item.getZonId(),item.getCreatedAt()));
        }
        AdsViewRequest adsViewRequest = new AdsViewRequest(
                MyUtils.getCurrentDateTime(),
                driverAdRequests);
        ApiRequests.syncAds(adsViewRequest, new BaseResponseInterface<List<AdModel>>() {
            @Override
            public void onSuccess(List<AdModel> adModels) {
                // set new list of ads
                dataBaseOperation.insertAdList(adModels);
                // delete ads driver watched it
                dataBaseOperation.removeAllDriverAdModel(driverAdModels.get(0).getId());
                // download new files
                // update home page
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }

    private void getAdsAndDownloadFiles(){
        // get all ads from data base
        DataBaseOperation baseOperation = new DataBaseOperation();
        List<AdModel> ads = baseOperation.getAllAds();
        downloadFile(ads);
    }

    private void downloadFile(final List<AdModel> ads) {
        if(iteration >= ads.size()) {
            return;
        }
        AdModel item = ads.get(iteration);
        // get image file
        String fileName = URLUtil.guessFileName(item.getAdUrl(), null, null);
        // file path
        String path;
        // detect type
        if(item.getTypeId() == Constant.IMAGE_AD){
            path =  getHomeController().imagePath ;
        }else if(item.getTypeId() == Constant.VIDEO_AD) {
            path = getHomeController().videoPath;
        }else {
            iteration++;
            downloadFile(ads);
            return;
        }
        File file = new File(path+fileName);
        if(!file.exists()){
            getHomeController().loadFile(item.getAdUrl(), path, new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    iteration++;
                    downloadFile(ads);
                }

                @Override
                public void onError(Error error) {
                    iteration++;
                    downloadFile(ads);
                }
            });
        }else {
            // file exsited so i will search second file
            iteration++;
            downloadFile(ads);
        }
    }
    private HomeController getHomeController(){
        if(controller == null)
            controller = new HomeController(this);
        return controller;
    }
}

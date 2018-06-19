package ntamtech.adinz.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.google.android.gms.location.LocationListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bassiouny.ahmed.genericmanager.SharedPrefManager;
import ntamtech.adinz.R;
import ntamtech.adinz.api.ApiRequests;
import ntamtech.adinz.api.apiModel.requests.AdsViewRequest;
import ntamtech.adinz.api.apiModel.requests.DriverAdRequest;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.BaseResponseInterface;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.model.DriverAdModel;
import ntamtech.adinz.model.DriverModel;
import ntamtech.adinz.model.ZoneModel;
import ntamtech.adinz.utils.Constant;
import ntamtech.adinz.utils.DownloadFiles;
import ntamtech.adinz.utils.MyUtils;
import ntamtech.adinz.utils.SharedPrefKey;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    // local variable
    private HomeController controller;
    private Location location;
    private DataBaseOperation dataBaseOperation;
    private List<AdModel> adModels;
    private int adsSize = 0;
    private int iteration = 0;
    private int driverId = 0;
    private boolean findLocation = false;

    // view
    private ImageView image, logo;
    private VideoView video;


    // override methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        onClick();
        // check location permission
        getController().requestPermission();
        // open location
        checkLocation();
        // get all ads from database
        getAllAds();
        // set driver id from shared pref
        driverId = SharedPrefManager.getObject(SharedPrefKey.USER, DriverModel.class).getId();
        waitToSync();
    }

    private void getAllAds() {
        adModels = getAdsFromDB();
        adsSize = adModels.size();
    }

    private void checkLocation() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Open GPS");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.show();
        }
    }
    /*private void load() {
        if(getDataBaseOperation().getDriverAdModelSize() < 2)
            return;
        final List<DriverAdModel> driverAdModels = dataBaseOperation.getAllDriverAdModelLimit();
        List<DriverAdRequest> driverAdRequests = new ArrayList<>();
        for(DriverAdModel item : driverAdModels){
            driverAdRequests.add(new DriverAdRequest(item.getAdvertisementId(),item.getDriverId(),item.getLatitude(),
                    item.getLongitude(),item.getZonId(),item.getCreatedAt()));
        }
        *//*AdsViewRequest adsViewRequest = new AdsViewRequest(
                MyUtils.getCurrentDateTime(),
                driverAdRequests);
        ApiRequests.syncAds(adsViewRequest, new BaseResponseInterface<List<AdModel>>() {
            @Override
            public void onSuccess(List<AdModel> adModels) {
                getDataBaseOperation().insertAdList(adModels);
                getDataBaseOperation().deleteDriverAdsModel();
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });*//*
    }*/

    private void playAds() {
        if (iteration >= adsSize) {
            iteration = 0;
        }
        AdModel item = adModels.get(iteration);
        String fileName = URLUtil.guessFileName(item.getAdUrl(), null, null);
        if (item.getTypeId() == Constant.IMAGE_AD) {
            image.setImageURI(Uri.parse(getController().imagePath + fileName));
            video.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            stopAppForSeconds();
        } else if (item.getTypeId() == Constant.VIDEO_AD) {
            // video ad
            video.setVideoURI(Uri.parse(getController().videoPath + fileName));
            video.setVisibility(View.VISIBLE);
            video.start();
            image.setVisibility(View.GONE);
            stopAppForSeconds();
        }
        viewAd(item.getId());

    }

    private void viewAd(int adId) {
        // save ad in database
        // send later to web service
        final DriverAdModel driverAdModel = new DriverAdModel();
        driverAdModel.setAdvertisementId(adId);
        driverAdModel.setCreatedAt(MyUtils.getCurrentDateTime());
        driverAdModel.setLatitude(location.getLatitude());
        driverAdModel.setLongitude(location.getLongitude());
        driverAdModel.setDriverId(driverId);
        // todo make zone id static till talk with aya
        driverAdModel.setZonId(1);
        // insert in database
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getDataBaseOperation().insertDriverAdModel(driverAdModel);
            }
        });
    }

    private void stopAppForSeconds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    iteration++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playAds();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (!findLocation) {
            playAds();
            findLocation = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getController().onRequestPermissionsResult(requestCode, grantResults);
    }


    // methods created by me
    private HomeController getController() {
        if (controller == null)
            controller = new HomeController(this);
        return controller;
    }

    private DataBaseOperation getDataBaseOperation() {
        if (dataBaseOperation == null)
            dataBaseOperation = new DataBaseOperation();
        return dataBaseOperation;
    }

    private void onClick() {
    }

    private void initView() {
        image = findViewById(R.id.image);
        video = findViewById(R.id.video);
        logo = findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ContactUsDialog.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getController().removeLocationListener();
    }

    public List<AdModel> getAdsFromDB() {
        // get ads from data base with limit 20
        List<AdModel> result = getDataBaseOperation().getAllAdsBetweenTwoIds(getController().startAdIndex, (getController().startAdIndex + getController().ADS_LIMIT_PER_SELECT));
        getController().startAdIndex = getController().startAdIndex + getController().ADS_LIMIT_PER_SELECT;
        return result;
    }

    private void waitToSync() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sync();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sync() {
        dataBaseOperation = new DataBaseOperation();
        if (dataBaseOperation.getDriverAdModelSize() < HomeController.SYNC_PER_COUNT)
            return;
        final List<DriverAdModel> driverAdModels = dataBaseOperation.getAllDriverAdModelLimit();
        List<DriverAdRequest> driverAdRequests = new ArrayList<>();
        for (DriverAdModel item : driverAdModels) {
            driverAdRequests.add(new DriverAdRequest(item.getAdvertisementId(), item.getDriverId(), item.getLatitude(),
                    item.getLongitude(), item.getZonId(), item.getCreatedAt()));
        }
        AdsViewRequest adsViewRequest = new AdsViewRequest(
                MyUtils.getCurrentDateTime(),
                driverAdRequests);
        ApiRequests.syncAds(adsViewRequest, new BaseResponseInterface<List<AdModel>>() {
            @Override
            public void onSuccess(List<AdModel> adModels) {
                if (adModels.size() == 0)
                    return;
                // set new list of ads
                dataBaseOperation.insertAdList(adModels);
                // delete ads driver watched it
                dataBaseOperation.removeAllDriverAdModel(driverAdModels.get(0).getId());
                // download new files
                DownloadFiles files = new DownloadFiles(HomeActivity.this);
                files.getAdsAndDownloadFiles(new CompleteInterface() {
                    @Override
                    public void onComplete() {
                        // update ads
                        getAllAds();
                    }
                });
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }

}

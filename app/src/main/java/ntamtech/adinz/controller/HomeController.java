package ntamtech.adinz.controller;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.google.android.gms.location.LocationListener;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.utils.LocationManager;

/**
 * Created by bassiouny on 24/04/18.
 */

public class HomeController {

    private LocationManager locationManager;
    private final int requestLocationPermission = 1;
    private final int requestStoragePermission = 2;
    private Activity activity;
    private LocationListener locationListener;
    private Realm realm;
    private String appPath = Environment.getExternalStorageDirectory() + "/Adinz/";
    private String imagePath = appPath + "Image/";
    private String videoPath = appPath + "Video/";

    public HomeController(Activity activity) {
        this.activity = activity;
        this.locationListener = (LocationListener) activity;
        // add realm config
        initRealmConfiguration();
        // create realm object
        realm = getRealm();
        // init PRDownloader
        PRDownloader.initialize(activity);
        // create basic folder
        File appFile = new File(appPath);
        File imageFile = new File(imagePath);
        File videoFile = new File(videoPath);

        if (!appFile.exists()) {
            appFile.mkdir();
        }
        if (!imageFile.exists()) {
            imageFile.mkdir();
        }
        if (!videoFile.exists()) {
            videoFile.mkdir();
        }
    }

    private void initRealmConfiguration() {
        Realm.init(activity);
    }

    private Realm getRealm() {
        if (realm == null)
            realm = Realm.getDefaultInstance();
        return realm;
    }

    public void closeRealm() {
        if (realm != null) {
            realm.close();
        }
    }

    // request to access location , storage (run time permission )
    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestLocationPermission);
        else
            initLocationManager();
    }

    // init my location manager
    private void initLocationManager() {
        locationManager = new LocationManager(activity, locationListener);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        if (grantResults.length == 0)
            return;
        else if (requestCode == requestLocationPermission && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            initLocationManager();
    }

    public void removeLocationListener() {
        if (locationListener == null || locationManager == null)
            return;
        locationManager.removeListener(locationListener);
    }

    public void createAd(int id, String url, String type) {
        realm.beginTransaction();
        AdModel mode = new AdModel(id, url, type);
        realm.insertOrUpdate(mode);
        realm.commitTransaction();
    }

    public void getAd() {
        RealmResults<AdModel> results = realm.where(AdModel.class).findAll();
        AdModel o = results.get(0);
        o.getId();
        o.getType();
    }

    public void loadImage(String url, OnDownloadListener onDownloadListener) {
        loadFile(url, imagePath, onDownloadListener);
    }

    public void loadVideo(String url, OnDownloadListener onDownloadListener) {
        loadFile(url, videoPath, onDownloadListener);
    }

    private void loadFile(String url, String dirPath, OnDownloadListener onDownloadListener) {
        PRDownloader.download(url, dirPath, URLUtil.guessFileName(url, null, null))
                .build()
                .start(onDownloadListener);
    }

   /* private String getFileNameFromUrl(String url) {
        return "";
    }*/

}

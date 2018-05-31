package ntamtech.adinz.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bassiouny.ahmed.genericmanager.SharedPrefManager;
import ntamtech.adinz.R;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.model.DriverAdModel;
import ntamtech.adinz.model.DriverModel;
import ntamtech.adinz.model.ZoneModel;
import ntamtech.adinz.utils.Constant;
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
    /*
            video.setVideoURI(Uri.parse(getController().videoPath+"test.mp4"));
            video.start();*/
        adModels = getAdsFromDB();
        adsSize = adModels.size();
        driverId = SharedPrefManager.getObject(SharedPrefKey.USER, DriverModel.class).getId();
        playAds();
    }

    private void playAds() {
        if (iteration >= adsSize) {
            iteration = 0;
        }
        AdModel item = adModels.get(iteration);
        String fileName = URLUtil.guessFileName(item.getAdUrl(), null, null);
        if (item.getTypeId() == Constant.IMAGE_AD) {
            // image ad
                /*Bitmap myBitmap = BitmapFactory.decodeFile(new File(getController().imagePath + fileName).getAbsolutePath());
                image.setImageBitmap(myBitmap);*/
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
        DriverAdModel driverAdModel = new DriverAdModel();
        driverAdModel.setAdvertisementId(adId);
        driverAdModel.setCreatedAt(MyUtils.getCurrentDateTime());
        driverAdModel.setLatitude(location.getLatitude());
        driverAdModel.setLongitude(location.getLongitude());
        driverAdModel.setDriverId(driverId);
        // todo make zone id static till talk with aya
        driverAdModel.setZonId(1);
        // insert in database
        getDataBaseOperation().insertOrUpdateDriverAdModel(driverAdModel);
    }

    private void stopAppForSeconds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
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

}

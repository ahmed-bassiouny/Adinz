package ntamtech.adinz.view;

import android.Manifest;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.google.android.gms.location.LocationListener;

import ntamtech.adinz.R;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.database.DataBaseOperation;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    // local variable
    private HomeController controller;
    private Location location;
    private final long SPLASH_TIME_OUT = 3000;
    // view
    private ImageView image;
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
        DataBaseOperation dataBaseOperation = new DataBaseOperation();
        dataBaseOperation.getAllAds();
        dataBaseOperation.getAllZone();
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

    private void onClick() {
    }

    private void initView() {
        image = findViewById(R.id.image);
        video = findViewById(R.id.video);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getController().removeLocationListener();
    }

}

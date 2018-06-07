package ntamtech.adinz.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;

import java.io.File;
import java.util.List;

import ntamtech.adinz.R;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.database.DataBaseOperation;
import ntamtech.adinz.interfaces.CompleteInterface;
import ntamtech.adinz.model.AdModel;
import ntamtech.adinz.utils.Constant;
import ntamtech.adinz.utils.DownloadFiles;

public class SyncActivity extends AppCompatActivity {

    private HomeController homeController;
    private int adsSize = 0;
    private int iteration = 0;
    private List<AdModel> ads;
    private int requestPermission =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        homeController = new HomeController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestPermission);
        else
            syncData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestPermission && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            syncData();

    }

    private void syncData(){
        DownloadFiles files = new DownloadFiles(this);
        files.getAdsAndDownloadFiles(new CompleteInterface() {
            @Override
            public void onComplete() {
                Toast.makeText(SyncActivity.this, "Sync Finished", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SyncActivity.this,HomeActivity.class));
                finish();
            }
        });
    }
}

package ntamtech.adinz.controller;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.LocationListener;

import io.realm.Realm;
import ntamtech.adinz.utils.LocationManager;

/**
 * Created by bassiouny on 24/04/18.
 */

public class HomeController {

    private LocationManager locationManager;
    private final int requestLocationPermission = 1;
    private Activity activity;
    private LocationListener locationListener;
    private Realm realm;

    public HomeController(Activity activity) {
        this.activity = activity;
        this.locationListener = (LocationListener) activity;
        // add realm config
        Realm.init(activity);
    }

    public Realm getRealm(){
        if(realm == null)
            realm = Realm.getDefaultInstance();
        return realm;
    }

    // request to access location (run time permission )
    public void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestLocationPermission);
        else
            initLocationManager();
    }
    // init my location manager
    private void initLocationManager(){
        locationManager = new LocationManager(activity,locationListener);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        if (requestCode == requestLocationPermission && grantResults[0] == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestLocationPermission);
        else if (requestCode == requestLocationPermission && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            initLocationManager();
    }

    public void removeLocationListener(){
        locationManager.removeListener(locationListener);
    }

}

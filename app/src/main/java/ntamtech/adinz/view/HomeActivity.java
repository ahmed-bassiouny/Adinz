package ntamtech.adinz.view;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.location.LocationListener;

import ntamtech.adinz.R;
import ntamtech.adinz.controller.HomeController;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    // local variable
    private HomeController controller;
    private Location location;
    private final long SPLASH_TIME_OUT = 3000;
    // view
    private ImageView image;


    // override methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        onClick();
        // check location permission
        getController().requestLocationPermission();

        getController().createAd(5,"url","ad");
        getController().createAd(66,"url22","ad22");
        getController().getAd();

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getController().removeLocationListener();
        getController().closeRealm();
    }

}

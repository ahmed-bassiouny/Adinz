package ntamtech.adinz.view;

import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.location.LocationListener;

import ntamtech.adinz.R;
import ntamtech.adinz.controller.HomeController;
import ntamtech.adinz.utils.LocationManager;
import ntamtech.adinz.utils.MyGlideApp;

public class HomeActivity extends AppCompatActivity implements LocationListener, EasyVideoCallback {

    // local variable
    private HomeController controller;
    private Location location;
    private final long SPLASH_TIME_OUT = 3000;
    // view
    private ImageView image;
    private EasyVideoPlayer player;


    // override methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        onClick();
        // check location permission
        getController().requestLocationPermission();


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

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        // todo make request
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

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
        player = findViewById(R.id.player);
    }

    // this method call web service
    // to get next ad
    private void getAd(){

    }
    // make video player hidden
    // show image view
    // load image with glide
    // in case load failed i will request next ad
    // in case load success stop app 30 sec
    private void showImage(String url) {
        player.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        MyGlideApp.setImage(this, image, url, new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {

                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // todo make request
                Toast.makeText(HomeActivity.this, "make request", Toast.LENGTH_SHORT).show();
            }
        }, SPLASH_TIME_OUT);
    }

    private void showVideo(String url){
        // Sets the callback to this Activity, since it inherits EasyVideoCallback
        player.setCallback(this);

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setSource(Uri.parse(url));
        player.disableControls();
        player.setAutoFullscreen(true);
        player.setAutoPlay(true);
    }

}

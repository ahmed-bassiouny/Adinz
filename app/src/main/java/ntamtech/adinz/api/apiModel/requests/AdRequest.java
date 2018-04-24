package ntamtech.adinz.api.apiModel.requests;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;

/**
 * Created by bassiouny on 24/04/18.
 */

public class AdRequest {

    @SerializedName(ApiKey.DRIVER_ID)
    private int driverId;
    @SerializedName(ApiKey.LAT)
    private double lat;
    @SerializedName(ApiKey.LNG)
    private double lng;

    public int getDriverId() {
        return driverId;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

package ntamtech.adinz.api.apiModel.requests;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;

public class DriverAdRequest {
    @SerializedName(ApiKey.ADVERTISEMENT)
    private int advertisementId;
    @SerializedName(ApiKey.DRIVER_ID)
    private int driverId;
    @SerializedName(ApiKey.LAT)
    private double latitude;
    @SerializedName(ApiKey.LNG)
    private double longitude;
    @SerializedName(ApiKey.ZONE_ID)
    private int zonId;
    @SerializedName(ApiKey.CREATE_AT)
    private String createdAt;

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setZonId(int zonId) {
        this.zonId = zonId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DriverAdRequest(int advertisementId, int driverId, double latitude, double longitude, int zonId, String createdAt) {
        this.advertisementId = advertisementId;
        this.driverId = driverId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zonId = zonId;
        this.createdAt = createdAt;
    }

    public DriverAdRequest() {
    }
}

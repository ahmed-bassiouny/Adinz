package ntamtech.adinz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ntamtech.adinz.api.apiModel.ApiKey;

public class AdDriverZoneModel {

    @SerializedName(ApiKey.DRIVER_INFO)
    private DriverModel driverModel;

    @SerializedName(ApiKey.ADS_INFO)
    private List<AdModel> adModels;

    @SerializedName(ApiKey.ZONES_INFO)
    private List<ZoneModel> zoneModels;

    public DriverModel getDriverModel() {
        if (driverModel == null)
            driverModel = new DriverModel();
        return driverModel;
    }

    public List<AdModel> getAdModels() {
        if (adModels == null)
            adModels = new ArrayList<>();
        return adModels;
    }

    public List<ZoneModel> getZoneModels() {
        if (zoneModels == null)
            zoneModels = new ArrayList<>();
        return zoneModels;
    }
}

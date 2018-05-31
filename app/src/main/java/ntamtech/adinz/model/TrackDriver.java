package ntamtech.adinz.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ntamtech.adinz.api.apiModel.ApiKey;

public class TrackDriver {

    @SerializedName(ApiKey.AD_DRIVER)
    private List<DriverAdModel> adModels;

    public TrackDriver(List<DriverAdModel> adModels) {
        if(adModels == null)
            adModels = new ArrayList<>();
        this.adModels = adModels;
    }
}

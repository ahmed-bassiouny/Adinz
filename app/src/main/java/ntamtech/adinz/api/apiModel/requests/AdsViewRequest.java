package ntamtech.adinz.api.apiModel.requests;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.model.DriverAdModel;
import ntamtech.adinz.model.TrackDriver;

public class AdsViewRequest {

    @SerializedName(ApiKey.UPDATED_AT)
    private String date;
    @SerializedName(ApiKey.TRACK_DRIVER)
    private List<DriverAdRequest> adModels;

    public AdsViewRequest(String date,List<DriverAdRequest> adModels) {
        this.adModels = adModels;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public List<DriverAdRequest> getAdModels() {
        return adModels;
    }

    public AdsViewRequest() {
    }
}

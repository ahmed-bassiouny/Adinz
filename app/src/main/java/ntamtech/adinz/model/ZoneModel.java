package ntamtech.adinz.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ntamtech.adinz.api.apiModel.ApiKey;

public class ZoneModel extends RealmObject {

    @SerializedName(ApiKey.ID)
    @PrimaryKey
    private String id;
    @SerializedName(ApiKey.NAME)
    private String name;
    @SerializedName(ApiKey.PRICE)
    private String price;
    @SerializedName(ApiKey.LAT)
    private String lat;
    @SerializedName(ApiKey.LNG)
    private String lng;
    @SerializedName(ApiKey.CITY_ID)
    private String cityId;


}

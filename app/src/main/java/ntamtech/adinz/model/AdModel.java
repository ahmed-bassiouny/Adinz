package ntamtech.adinz.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import ntamtech.adinz.api.apiModel.ApiKey;

/**
 * Created by bassiouny on 24/04/18.
 */

public class AdModel extends RealmObject {

    @PrimaryKey
    @SerializedName(ApiKey.ID)
    private String id;
    @SerializedName(ApiKey.NAME)
    private String name;
    @SerializedName(ApiKey.DESCRIPTION)
    private String description;
    @SerializedName(ApiKey.PRICE)
    private String price;
    @SerializedName(ApiKey.START_DATE)
    private String start_date;
    @SerializedName(ApiKey.END_DATE)
    private String end_date;
    @SerializedName(ApiKey.TYPE_ID)
    private String type_id;
    @SerializedName(ApiKey.URL)
    private String adUrl;


}

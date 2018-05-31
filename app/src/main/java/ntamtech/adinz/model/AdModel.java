package ntamtech.adinz.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.utils.MyUtils;

/**
 * Created by bassiouny on 24/04/18.
 */

public class AdModel extends RealmObject {

    @PrimaryKey
    @SerializedName(ApiKey.ID)
    private int id;
    @SerializedName(ApiKey.NAME)
    private String name;
    @SerializedName(ApiKey.DESCRIPTION)
    private String description;
    @SerializedName(ApiKey.PRICE)
    private String price;
    @SerializedName(ApiKey.START_DATE)
    private String startDate;
    @SerializedName(ApiKey.END_DATE)
    private String endDate;
    @SerializedName(ApiKey.TYPE_ID)
    private String typeId;
    @SerializedName(ApiKey.URL)
    private String adUrl;

    public int getTypeId() {
        try {
            return Integer.parseInt(MyUtils.getString(typeId));
        } catch (Exception e) {
            return 0;
        }
    }

    public String getAdUrl() {
        return MyUtils.getString(adUrl);
    }

    public int getId() {
        return id;
    }
}

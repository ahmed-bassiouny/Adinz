package ntamtech.adinz.model;

import android.os.Parcel;
import android.os.Parcelable;
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

public class AdModel extends RealmObject implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.price);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.typeId);
        dest.writeString(this.adUrl);
    }

    public AdModel() {
    }

    protected AdModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.price = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.typeId = in.readString();
        this.adUrl = in.readString();
    }

    public static final Parcelable.Creator<AdModel> CREATOR = new Parcelable.Creator<AdModel>() {
        @Override
        public AdModel createFromParcel(Parcel source) {
            return new AdModel(source);
        }

        @Override
        public AdModel[] newArray(int size) {
            return new AdModel[size];
        }
    };
}

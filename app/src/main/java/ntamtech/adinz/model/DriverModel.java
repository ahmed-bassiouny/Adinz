package ntamtech.adinz.model;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.utils.MyUtils;

public class DriverModel {

    @SerializedName(ApiKey.ID)
    private String id;
    @SerializedName(ApiKey.NAME)
    private String name;
    @SerializedName(ApiKey.PHONE)
    private String phone;
    @SerializedName(ApiKey.EMAIL)
    private String email;
    @SerializedName(ApiKey.NOTIFICATION_TOKEN)
    private String notificationToken;
    @SerializedName(ApiKey.BLOCKED)
    private String isBlocked;

    public int getId() {
        try {
            return Integer.parseInt(MyUtils.getString((id)));
        } catch (Exception e) {
            return 0;
        }
    }

    public String getName() {
        return MyUtils.getString(name);
    }

    public String getPhone() {
        return MyUtils.getString(phone);
    }

    public String getEmail() {
        return MyUtils.getString(email);
    }


    public boolean getIsBlocked() {
        try {
            return Boolean.parseBoolean(MyUtils.getString(isBlocked));
        } catch (Exception e) {
            return false;
        }
    }
}

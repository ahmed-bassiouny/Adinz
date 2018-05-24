package ntamtech.adinz.model;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;

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

}

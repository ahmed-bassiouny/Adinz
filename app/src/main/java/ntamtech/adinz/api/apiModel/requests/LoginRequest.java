package ntamtech.adinz.api.apiModel.requests;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;


public class LoginRequest {

    @SerializedName(ApiKey.EMAIL)
    private String driverCode;
    @SerializedName(ApiKey.PASSWORD)
    private String password;
    @SerializedName(ApiKey.NOTIFICATION_TOKEN)
    private String notificationToken;

    public LoginRequest(String driverCode,String password,String notificationToken) {
        this.driverCode = driverCode;
        this.password = password;
        this.notificationToken = notificationToken;
    }
}


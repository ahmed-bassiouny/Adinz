package ntamtech.adinz.api.apiModel.requests;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;


public class LoginRequest {

    @SerializedName(ApiKey.EMAIL)
    private String email;
    @SerializedName(ApiKey.PASSWORD)
    private String password;
    @SerializedName(ApiKey.NOTIFICATION_TOKEN)
    private String notificationToken;

    private LoginRequest(Builder builder) {
        email = builder.email;
        password = builder.password;
        notificationToken = builder.notificationToken;
    }


    public static final class Builder {
        private String email;
        private String password;
        private String notificationToken;

        public Builder() {
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder notificationToken(String val) {
            notificationToken = val;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this);
        }
    }
}


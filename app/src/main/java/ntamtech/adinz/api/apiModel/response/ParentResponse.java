package ntamtech.adinz.api.apiModel.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.utils.MyUtils;

/**
 * Created by bassiouny on 24/04/18.
 */

public abstract class ParentResponse <T>{

    @SerializedName(ApiKey.STATUS)
    @SuppressWarnings({"UnusedDeclaration"})
    private boolean status;
    @SerializedName(ApiKey.MESSAGE)
    @SuppressWarnings({"UnusedDeclaration"})
    private String message;

    public boolean getStatus() {
        return status;
    }
    @NonNull
    public String getMessage() {
        return MyUtils.getString(message);
    }

    public abstract T getObject();
}

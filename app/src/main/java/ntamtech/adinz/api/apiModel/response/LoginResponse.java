package ntamtech.adinz.api.apiModel.response;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.model.AdDriverZoneModel;
import ntamtech.adinz.model.AdModel;

public class LoginResponse extends ParentResponse<AdDriverZoneModel> {

    @SerializedName(ApiKey.DATA)
    private AdDriverZoneModel model;

    @Override
    public AdDriverZoneModel getObject() {
        if (model == null)
            model = new AdDriverZoneModel();
        return model;
    }
}

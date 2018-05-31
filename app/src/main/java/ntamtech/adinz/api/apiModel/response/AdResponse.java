package ntamtech.adinz.api.apiModel.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.model.AdDriverZoneModel;
import ntamtech.adinz.model.AdModel;

/**
 * Created by bassiouny on 24/04/18.
 */

public class AdResponse extends ParentResponse<List<AdModel>> {

    @SerializedName(ApiKey.DATA)
    private AdDriverZoneModel model;

    @Override
    public List<AdModel> getObject() {
        if (model == null)
            model = new AdDriverZoneModel();
        return model.getAdModels();
    }
}

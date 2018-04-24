package ntamtech.adinz.api.apiModel.response;

import com.google.gson.annotations.SerializedName;

import ntamtech.adinz.api.apiModel.ApiKey;
import ntamtech.adinz.model.AdModel;

/**
 * Created by bassiouny on 24/04/18.
 */

public class AdResponse extends ParentResponse<AdModel> {

    @SerializedName(ApiKey.DATA)
    private AdModel adModel;

    @Override
    public AdModel getObject() {
        if (adModel == null)
            adModel = new AdModel();
        return adModel;
    }
}

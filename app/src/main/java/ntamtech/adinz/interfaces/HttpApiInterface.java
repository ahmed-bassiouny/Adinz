package ntamtech.adinz.interfaces;

import ntamtech.adinz.api.apiModel.response.AdResponse;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by bassiouny on 24/04/18.
 */

public interface HttpApiInterface {

    @POST()
    Call<AdResponse> getAd();
}

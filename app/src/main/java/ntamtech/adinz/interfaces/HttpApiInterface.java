package ntamtech.adinz.interfaces;

import ntamtech.adinz.api.apiModel.requests.AdsViewRequest;
import ntamtech.adinz.api.apiModel.requests.LoginRequest;
import ntamtech.adinz.api.apiModel.response.AdResponse;
import ntamtech.adinz.api.apiModel.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by bassiouny on 24/04/18.
 */

public interface HttpApiInterface {

    @POST()
    Call<AdResponse> getAd();

    @POST("driver_login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


    @POST("advertisment")
    Call<AdResponse> syncAds(@Body AdsViewRequest adsViewRequest);
}

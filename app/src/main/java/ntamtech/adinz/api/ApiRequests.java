package ntamtech.adinz.api;

import android.support.annotation.NonNull;

import java.util.List;

import ntamtech.adinz.api.apiModel.requests.AdsViewRequest;
import ntamtech.adinz.api.apiModel.requests.LoginRequest;
import ntamtech.adinz.api.apiModel.response.AdResponse;
import ntamtech.adinz.api.apiModel.response.LoginResponse;
import ntamtech.adinz.api.apiModel.response.ParentResponse;
import ntamtech.adinz.interfaces.BaseResponseInterface;
import ntamtech.adinz.model.AdDriverZoneModel;
import ntamtech.adinz.model.AdModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bassiouny on 24/04/18.
 */

public class ApiRequests {

    private static final String apiError = "Server not responding";

    @SuppressWarnings("unchecked")
    private static <T> void checkValidResult(T response, BaseResponseInterface anInterface) {
        // get response body
        Response<ParentResponse> parentResponse = (Response<ParentResponse>) response;
        ParentResponse body = parentResponse.body();
        if (body == null) {
            // if body == null this mean respond from server total bad
            anInterface.onFailed(apiError);
            return;
        }
        // check response code from server
        if (parentResponse.code() == 200) {
            // check status key from server
            if (body.getStatus()) {
                // i make sure this object which i need
                anInterface.onSuccess(body.getObject());
            } else {
                // something happened and server tell me what i should do
                anInterface.onFailed(body.getMessage());
            }
        } else {
            // this case mean response code not equal 200
            anInterface.onFailed(parentResponse.message());
        }
    }

    public static void syncAds(AdsViewRequest adsViewRequest, final BaseResponseInterface<List<AdModel>> anInterface) {
        Call<AdResponse> response = ApiConfig.httpApiInterface.syncAds(adsViewRequest);
        response.enqueue(new Callback<AdResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdResponse> call, @NonNull Response<AdResponse> response) {
                // check on response and get data
                checkValidResult(response, anInterface);
            }

            @Override
            public void onFailure(@NonNull Call<AdResponse> call, @NonNull Throwable t) {
                // get error message
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void login(LoginRequest loginRequest, final BaseResponseInterface<AdDriverZoneModel> anInterface) {
        Call<LoginResponse> response = ApiConfig.httpApiInterface.login(loginRequest);
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                // check on response and get data
                checkValidResult(response, anInterface);
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                // get error message
                anInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

}

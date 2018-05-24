package ntamtech.adinz.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ntamtech.adinz.R;
import ntamtech.adinz.api.ApiRequests;
import ntamtech.adinz.api.apiModel.requests.LoginRequest;
import ntamtech.adinz.interfaces.BaseResponseInterface;
import ntamtech.adinz.model.AdDriverZoneModel;
import ntamtech.adinz.utils.DummyData;

public class SigninActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText etDriverCode, etDriverPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
    }

    private void initView() {
        // find view by id
        etDriverCode = findViewById(R.id.et_driver_code);
        etDriverPassword = findViewById(R.id.et_driver_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress);

        // onclick
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDriverCode.getText().toString().trim().isEmpty()) {
                    etDriverCode.setError(getString(R.string.driver_code_empty));
                } else if (etDriverPassword.getText().toString().trim().isEmpty()) {
                    etDriverCode.setError(null);
                    etDriverPassword.setError(getString(R.string.driver_password_empty));
                } else {
                    etDriverCode.setError(null);
                    etDriverPassword.setError(null);
                    loginAndCacheData();
                }
            }
        });
    }

    private void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
    }

    private void stopLoading() {
        progressBar.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    private void loginAndCacheData() {
        startLoading();
        LoginRequest request = new LoginRequest(
                etDriverCode.getText().toString(),
                etDriverPassword.getText().toString(),
                DummyData.notification);
        ApiRequests.login(request, new BaseResponseInterface<AdDriverZoneModel>() {
            @Override
            public void onSuccess(AdDriverZoneModel adDriverZoneModel) {
                // todo cache data
                Toast.makeText(SigninActivity.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(SigninActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                stopLoading();
            }
        });
    }
}

package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bhopalplus.Model.SignupData;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivitySignUpBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.ReturnErrorToast;
import com.bhopalplus.utils.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    String stMobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stMobile = binding.loginPhone.getText().toString();
                if (TextUtils.isEmpty(stMobile)) {
                    binding.loginPhone.setError("Please enter Mobile Number !");
                    binding.loginPhone.requestFocus();
                }
                else {
                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getApplicationContext())) {
                        sendData();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        binding.txLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }



private void sendData() {

        Map<String, String> param = new HashMap<>();
        param.put("mobile", stMobile);
        Call<SignupData> call = APIClient.getAPIClient().userRegistration(param);
        call.enqueue(new Callback<SignupData>() {
            @Override
            public void onResponse(@NonNull Call<SignupData> call, @NonNull Response<SignupData> response) {
                Log.e("check", response.toString());
                SignupData signupData = response.body();

                if (signupData.getMessage().equals("This number is already registered.")) {
                    Toast.makeText(SignUpActivity.this, "Number is already registered", Toast.LENGTH_SHORT).show();
                }
                else {
                    SignupData.Data userdata=signupData.getData();
                    SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, userdata.getMobile());
                    SharedHelper.putKey(getApplicationContext(), AppConstats.GetOtp, userdata.getOtp());
                    startActivity(new Intent(getApplicationContext(), OtpVerifyActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SignupData> call, @NonNull Throwable t) {
                Log.e("sjduwoiej", t.getMessage(), t);

            }
        });
    }




}


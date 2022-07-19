package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bhopalplus.Model.SignupData;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivitySignUpBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.ReturnErrorToast;
import com.bhopalplus.utils.SharedHelper;

import java.util.HashMap;
import java.util.Map;

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
                } else {
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
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(SignUpActivity.this);
                }

                SignupData signupData = response.body();
                if (signupData != null) {
                    if (signupData.getResult()) {

                        SignupData.Data userdata=signupData.getData();
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, String.valueOf(userdata.getUserId()));
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, userdata.getMobile());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.GetOtp, userdata.getOtp());
                        startActivity(new Intent(SignUpActivity.this, OtpVerifyActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, signupData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SignupData> call, @NonNull Throwable t) {
                Log.e("sjduwoiej", t.getMessage(), t);
            }
        });
    }

/*  private void sendData() {


        AndroidNetworking.post("http://128.199.176.121/bhopalplusbnest/api/registration")
                .addBodyParameter("mobile", stMobile)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("MobileVerifyActivity", "response: " + response);
                        try {
                            if (response.getString("result").equals("true")) {
                               // Toasty.success(SignUpActivity.this, response.getString("message"), Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), OtpVerifyActivity.class));
                            } else {
                                Toasty.success(SignUpActivity.this, response.getString("message"), Toasty.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            Log.e("bgh", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("hjkjk", "onError: " + anError.getMessage());


                    }
                });


    }*/


}


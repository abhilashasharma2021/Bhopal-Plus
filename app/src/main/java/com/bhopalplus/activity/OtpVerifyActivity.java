package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.OtpVerifyData;
import com.bhopalplus.Model.ResendOtpData;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityOtpVerifyBinding;
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

public class OtpVerifyActivity extends AppCompatActivity {
    ActivityOtpVerifyBinding binding;
    String getOTP = "", getMobile = "", getUserID = "", pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getMobile = SharedHelper.getKey(getApplicationContext(), AppConstats.USER_MOBILE);
        getOTP = SharedHelper.getKey(getApplicationContext(), AppConstats.GetOtp);
        Log.e("check",getOTP );

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pin = binding.pinView.getText().toString();

                binding.pinView.setAnimationEnable(true);
                if (pin.length() != 4) {

                    Toast.makeText(OtpVerifyActivity.this, "Please enter 4 digit otp", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("ksjldxks", pin);
                    if (getOTP.equals(pin)) {
                        InternetConnectionInterface connectivity = new InternetConnectivity();
                        if (connectivity.isConnected(getApplicationContext())) {

                         verify_otp();
                        } else {
                            Toast.makeText(OtpVerifyActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(OtpVerifyActivity.this, "You have entered wrong otp", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });

        binding.txResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend_otp();
            }
        });


    }

    private void verify_otp(){

        Map<String, String> param = new HashMap<>();
        param.put("mobile", getMobile);
        param.put("otp", getOTP);
        Call<OtpVerifyData> call = APIClient.getAPIClient().otpVerify(param);
        call.enqueue(new Callback<OtpVerifyData>() {
            @Override
            public void onResponse(@NonNull Call<OtpVerifyData> call, @NonNull Response<OtpVerifyData> response) {
                Log.e("xzcxv", response.toString());
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(OtpVerifyActivity.this);
                }

                OtpVerifyData otpVerifyData = response.body();
                if (otpVerifyData != null) {
                    if (otpVerifyData.getResult()) {
                        OtpVerifyData.Data userData=otpVerifyData.getData();


                        new CountDownTimer(60000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                binding.txCounter.setText("seconds remaining: " + millisUntilFinished / 1000);
                                binding.txResend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(OtpVerifyActivity.this, "Wait for ${millisUntilFinished / 1000} seconds", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            public void onFinish() {

                                binding.txResend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        resend_otp();
                                    }
                                });
                                binding.txCounter.setVisibility(View.GONE);
                            }

                        }.start();

                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID,String.valueOf(userData.getId()) );
                        startActivity(new Intent(OtpVerifyActivity.this, AddUserDetailsActivity.class));
                        finish();

                         /* *//*complete_profile=0 means profile not completed and 1 means completed*//*
                        if (String.valueOf(userData.getCompleteProfile()).equals("0")){

                        }else {
                            startActivity(new Intent(OtpVerifyActivity.this, MainActivity.class));
                            finish();
                        }*/


                    } else {
                        Toast.makeText(OtpVerifyActivity.this, otpVerifyData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<OtpVerifyData> call, @NonNull Throwable t) {
                Log.e("check", t.getMessage(), t);
            }
        });

    }





    private void resend_otp(){

        Map<String, String> param = new HashMap<>();
        param.put("mobile", getMobile);
        param.put("id", getUserID);
        Call<ResendOtpData> call = APIClient.getAPIClient().resendOtp(param);
        call.enqueue(new Callback<ResendOtpData>() {
            @Override
            public void onResponse(@NonNull Call<ResendOtpData> call, @NonNull Response<ResendOtpData> response) {
                Log.e("check", response.toString());
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(OtpVerifyActivity.this);
                }

                ResendOtpData resendOtpData = response.body();
                if (resendOtpData != null) {
                    if (resendOtpData.getResult()) {
                      timer();

                    } else {
                        Toast.makeText(OtpVerifyActivity.this, resendOtpData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResendOtpData> call, @NonNull Throwable t) {
                Log.e("edfefe", t.getMessage(), t);
            }
        });

    }

    private void timer(){


      binding.txResend.setText("Otp Sent Successfully");
      binding.txCounter.setVisibility(View.VISIBLE);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.txCounter.setText("seconds remaining: " + millisUntilFinished / 1000);
                binding.txResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(OtpVerifyActivity.this, "Wait for ${millisUntilFinished / 1000} seconds", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void onFinish() {
               binding.txResend.setText("Resend Otp");
               binding.txResend.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       resend_otp();
                   }
               });
              binding.txCounter.setVisibility(View.GONE);
            }

        }.start();

    }

}
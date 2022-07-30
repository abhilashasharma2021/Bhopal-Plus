package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.bhopalplus.Model.LoginData;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityLoginBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.SharedHelper;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
String getUserMobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         binding.txSign.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                 finish();
             }
         });



        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserMobile = binding.loginPhone.getText().toString();
                if (TextUtils.isEmpty(getUserMobile)) {
                    binding.loginPhone.setError("Please enter Mobile Number !");
                    binding.loginPhone.requestFocus();
                } else if (getUserMobile.length()<10) {
                    binding.loginPhone.setError("Please enter atleast 10 digit registered mobile number");
                    binding.loginPhone.requestFocus();
                }
                else {
                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getApplicationContext())) {
                        login(getUserMobile);
                    } else {
                        Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });



      if (getUserMobile.equals("")){
            binding.loginPhone.setHint("Enter Mobile Number");
        }else {
            binding.cbRember.setChecked(true);
            binding.loginPhone.setText(getUserMobile);
        }

        binding.cbRember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    String getMobile=binding.loginPhone.getText().toString().trim();
                    binding.loginPhone.setText(getMobile);
                    SharedHelper.putKey(LoginActivity.this, AppConstats.USER_MOBILE,binding.loginPhone.getText().toString().trim());


                }
            }
        });


    }



   private void login(String getUserMobile) {

        Map<String, String> param = new HashMap<>();
        param.put("mobile", getUserMobile);
        Call<LoginData> call = APIClient.getAPIClient().userLogin(param);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                LoginData loginData = response.body();
                  if(loginData.getMessage().equals("This number is not registred.")){
                   //   loginData.getData().getMobile();
                      Toast.makeText(LoginActivity.this, "This Number is not register", Toast.LENGTH_SHORT).show();
                  }

                  else{

                      LoginData.Data userdata=loginData.getData();
                      SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, String.valueOf(userdata.getUserId()));
                      SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, userdata.getMobile());
                      SharedHelper.putKey(getApplicationContext(), AppConstats.USER_TOKEN, userdata.getToken());
                      startActivity(new Intent(LoginActivity.this, MainActivity.class));
                      finish();

                  }

            }

            @Override
            public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {
                Log.e("sjduwoiej", t.getMessage(), t);
            }
        });
    }
}
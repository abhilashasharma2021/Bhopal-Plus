package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import com.bhopalplus.Model.AddDetailsData;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityAddUserDetailsBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.ReturnErrorToast;
import com.bhopalplus.utils.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserDetailsActivity extends AppCompatActivity {
    ActivityAddUserDetailsBinding binding;
    String stName = "", stEmail = "", stAddress = "", stDob = "", stGender = "",getUserID="";
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserID = SharedHelper.getKey(getApplicationContext(), AppConstats.USER_ID);

        Log.e("dncjdcn", getUserID );



        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stEmail = binding.etEmail.getText().toString();
                stName = binding.etName.getText().toString();
                stAddress = binding.etAddress.getText().toString();
                stDob = binding.etDob.getText().toString();
                stGender = binding.etGender.getText().toString();
                if (TextUtils.isEmpty(stEmail)) {
                    binding.etEmail.setError("Please enter Email Id !");
                    binding.etEmail.requestFocus();
                } else if (TextUtils.isEmpty(stName)) {
                    binding.etName.setError("Please enter Name !");
                    binding.etName.requestFocus();
                }  else if (TextUtils.isEmpty(stAddress)) {
                    binding.etAddress.setError("Please enter Address !");
                    binding.etAddress.requestFocus();
                } else if (TextUtils.isEmpty(stDob)) {
                    binding.etDob.setError("Please enter Date of Birth !");
                    binding.etDob.requestFocus();
                } else if (TextUtils.isEmpty(stGender)) {
                    binding.etGender.setError("Please enter Gender !");
                    binding.etGender.requestFocus();
                } else {
                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getApplicationContext())) {
                        // startActivity(new Intent(AddUserDetailsActivity.this,MainActivity.class));
                        addDetails();
                    } else {
                        Toast.makeText(AddUserDetailsActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

               /* stEmail = binding.etEmail.getText().toString();
                stName = binding.etName.getText().toString();
                stAddress = binding.etAddress.getText().toString();
                stDob = binding.etDob.getText().toString();
                stGender = binding.etGender.getText().toString();
               if (TextUtils.isEmpty(stEmail)) {
                    binding.etEmail.setError("Please enter Email Id !");
                    binding.etEmail.requestFocus();
                } else if (TextUtils.isEmpty(stName)) {
                    binding.etName.setError("Please enter Name !");
                    binding.etName.requestFocus();
                }  else if (TextUtils.isEmpty(stAddress)) {
                    binding.etAddress.setError("Please enter Address !");
                    binding.etAddress.requestFocus();
                } else if (TextUtils.isEmpty(stDob)) {
                    binding.etDob.setError("Please enter Date of Birth !");
                    binding.etDob.requestFocus();
                } else if (TextUtils.isEmpty(stGender)) {
                    binding.etGender.setError("Please enter Gender !");
                    binding.etGender.requestFocus();
                } else {
                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getApplicationContext())) {
                        addDetailsData();
                    } else {
                        Toast.makeText(AddUserDetailsActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

    }


    private void addDetails(){

        AndroidNetworking.post("http://128.199.176.121/bhopalplusbnest/api/adddetails")
                .addBodyParameter("id", getUserID)
                .addBodyParameter("email", stEmail)
                .addBodyParameter("name", stName)
                .addBodyParameter("address", stAddress)
                .addBodyParameter("dob", stDob)
                .addBodyParameter("gender", stGender)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("rthtrh", "response: " + response);
                        try {
                            if (response.getString("result").equals("true")) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {

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


    }




    private void addDetailsData(){


        Log.e("ndjncj",getUserID );
        Log.e("ndjncj",stAddress );
        Log.e("ndjncj",stEmail );
        Log.e("ndjncj",stGender );
        Log.e("ndjncj",stDob );
        Log.e("ndjncj",stName );
        Map<String, String> param = new HashMap<>();
        param.put("id", getUserID);
        param.put("email", stEmail);
        param.put("name", stName);
        param.put("address", stAddress);
        param.put("dob", stDob);
        param.put("gender", stGender);
        Call<AddDetailsData> call = APIClient.getAPIClient().addDetails(param);
        call.enqueue(new Callback<AddDetailsData>() {
            @Override
            public void onResponse(@NonNull Call<AddDetailsData> call, @NonNull Response<AddDetailsData> response) {
                Log.e("vegdvgv", response.toString());
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(AddUserDetailsActivity.this);
                }

                AddDetailsData addDetailsData = response.body();
                if (addDetailsData != null) {
                    if (addDetailsData.getResult()) {

                        AddDetailsData.Data userdata=addDetailsData.getData();
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, String.valueOf(userdata.getUserId()));
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, userdata.getEmail());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, userdata.getName());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, userdata.getAddress());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DOB, userdata.getDob());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_GENDER, userdata.getGender());
                        startActivity(new Intent(AddUserDetailsActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(AddUserDetailsActivity.this, addDetailsData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<AddDetailsData> call, @NonNull Throwable t) {
                Log.e("hdghfdgh", t.getMessage(), t);
            }
        });

    }
}
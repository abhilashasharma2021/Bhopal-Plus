package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserDetailsActivity extends AppCompatActivity {
    ActivityAddUserDetailsBinding binding;
    String stName = "", stEmail = "", stAddress = "", stDob = "", getUserID = "", StrFinalStatus = "";

    final static String DATE_FORMAT = "dd-MM-yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserID = SharedHelper.getKey(getApplicationContext(), AppConstats.USER_ID);


        Log.e("dncjdcn", getUserID);

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = binding.radioGroup.findViewById(checkedId);
                int index = binding.radioGroup.indexOfChild(radioButton);

                Log.e("wedgdsgdf", index+"" );
                switch (index) {
                    case 0:
                        StrFinalStatus = "male";
                        break;
                    case 1:
                        StrFinalStatus = "Female";
                        break;
                    case 2:
                        StrFinalStatus = "Others";
                        break;
                }
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stEmail = binding.etEmail.getText().toString();
                stName = binding.etName.getText().toString();
                stAddress = binding.etAddress.getText().toString();
                stDob = binding.etDob.getText().toString();

                if (validation()) {

                    if (!validateEmailAddress(stEmail)) {
                        Toast.makeText(AddUserDetailsActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }

                    else if (!isDateValid(stDob)){
                        Toast.makeText(AddUserDetailsActivity.this, "Invalid Dob", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        InternetConnectionInterface connectivity = new InternetConnectivity();
                        if (connectivity.isConnected(getApplicationContext())) {
                            addDetails(stEmail, stName, stAddress, stDob, StrFinalStatus);
                        } else {
                            Toast.makeText(AddUserDetailsActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    validation();
                }


            }
        });


    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private Boolean validation(){

        Boolean boolen=false;
        if (binding.etEmail.getText().toString().isEmpty()){

            binding.etEmail.setError("Email Address Must Required");
        }else  if (binding.etName.getText().toString().isEmpty()){
            binding.etName.setError("Name Must Required");
        }else  if (binding.etAddress.getText().toString().isEmpty()) {
            binding.etAddress.setError("Address Must Required");
        }

        else  if (binding.etDob.getText().toString().isEmpty()) {
            binding.etDob.setError("Dob Must Required");
        }
        else if (StrFinalStatus.isEmpty()) {

            Toast.makeText(AddUserDetailsActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
        }
        else {
            boolen=true;

        }
        return boolen;
    }
    public static boolean validateEmailAddress(String stEmail)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(stEmail);
        return matcher.matches();
    }
    private void addDetails(String stEmail, String stName, String stAddress, String stDob, String StrFinalStatus){

        Map<String, String> param = new HashMap<>();
        param.put("id", getUserID);
        param.put("email", stEmail);
        param.put("name", stName);
        param.put("address", stAddress);
        param.put("dob", stDob);
        param.put("gender", StrFinalStatus);
        Call<AddDetailsData> call = APIClient.getAPIClient().addDetails(param);
        call.enqueue(new Callback<AddDetailsData>() {
            @Override
            public void onResponse(@NonNull Call<AddDetailsData> call, @NonNull Response<AddDetailsData> response) {
                Log.e("vegdvgv", response.toString());
                if (!response.isSuccessful()) {
                    Toast.makeText(AddUserDetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                  //  ReturnErrorToast.showToast(AddUserDetailsActivity.this);
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
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_TOKEN, userdata.getToken());
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
package com.bhopalplus.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.ShowProfileModel;
import com.bhopalplus.Model.UpdateProfileModel;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.FragmentUserProfileBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.SharedHelper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFrag extends Fragment {
    FragmentUserProfileBinding binding;
    View view;
    Context context;

    String stName = "", stEmail = "", stAddress = "", stDob = "", stGender = "", getUserToken = "", getUserId = "";

    final static String DATE_FORMAT = "dd-MM-yyyy";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentUserProfileBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();
        context = getActivity();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = binding.radioGroup.findViewById(checkedId);
                int index = binding.radioGroup.indexOfChild(radioButton);

                Log.e("wedgdsgdf", index + "");
                switch (index) {
                    case 0:

                        stGender = "male";
                        break;
                    case 1:
                        stGender = "female";
                        break;

                    case 2:
                        stGender = "other";
                        break;
                }
            }
        });


        getUserToken = SharedHelper.getKey(getActivity(), AppConstats.USER_TOKEN);
        getUserId = SharedHelper.getKey(getActivity(), AppConstats.USER_ID);

        Log.e("jdnchd", "onCreateView: " + getUserToken);
        Log.e("jdnchd", "onCreateView: " + getUserId);
        showDetails();
        binding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stEmail = binding.etEmail.getText().toString();
                stName = binding.etName.getText().toString();
                stAddress = binding.etAddress.getText().toString();
                stDob = binding.etDob.getText().toString();

                if (!validateEmailAddress(stEmail)) {
                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
                } else if (!isDateValid(stDob)) {
                    Toast.makeText(getActivity(), "Invalid Dob", Toast.LENGTH_SHORT).show();
                } else {
                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getActivity())) {
                        update_Profile(stName, stEmail, stAddress, stDob, stGender);

                    } else {
                        Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;

    }

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validateEmailAddress(String stEmail) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(stEmail);
        return matcher.matches();
    }

    private void showDetails() {


        Call<ShowProfileModel> call = APIClient.getAPIClient().showProfile("Bearer " + getUserToken);
        call.enqueue(new Callback<ShowProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowProfileModel> call, @NonNull Response<ShowProfileModel> response) {
                Log.e("vegdvgv", response.toString());
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }

                ShowProfileModel addDetailsData = response.body();
                if (addDetailsData != null) {
                    if (addDetailsData.getResult()) {

                        binding.etEmail.setText(addDetailsData.getData().getEmail());
                        binding.etName.setText(addDetailsData.getData().getName());
                        binding.etAddress.setText(addDetailsData.getData().getAddress());
                        binding.etDob.setText(addDetailsData.getData().getDob());
                        binding.txName.setText("Hello ! " + addDetailsData.getData().getName());

                        if (addDetailsData.getData().getGender().equals("male")) {
                            binding.radioMale.setChecked(true);
                        } else if (addDetailsData.getData().getGender().equals("female")) {
                            binding.radioFemale.setChecked(true);
                        } else {
                            binding.radioOther.setChecked(true);
                        }


                    } else {
                        Toast.makeText(getActivity(), addDetailsData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ShowProfileModel> call, @NonNull Throwable t) {
                Log.e("gtrggt", t.getMessage(), t);
            }
        });

    }


    private void update_Profile(String stName, String stEmail, String stAddress, String stDob, String stGender) {

        Call<UpdateProfileModel> call = APIClient.getAPIClient().updateProfile(getUserId, stName, stEmail, stAddress, stDob, stGender, "Bearer " + getUserToken);
        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileModel> call, @NonNull Response<UpdateProfileModel> response) {
                Log.e("dddsvfsv", response.toString());
                UpdateProfileModel updateData = response.body();
                if(updateData.getMessage().equals("User not found")){

                    Toast.makeText(getActivity(), updateData.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), updateData.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileModel> call, @NonNull Throwable t) {
                Log.e("hgrtyhrtyhtr", t.getMessage(), t);
            }
        });
    }
}



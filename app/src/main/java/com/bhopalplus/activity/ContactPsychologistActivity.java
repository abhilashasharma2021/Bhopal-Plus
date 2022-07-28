package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bhopalplus.Adapters.PsychologistAdapter;
import com.bhopalplus.Adapters.TeleAdapter;
import com.bhopalplus.Data.ContactPsychologistData;

import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.ContactpsychlogistModel;

import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityContactPsychologistBinding;

import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.MyDialog.CustomDialog;
import com.bhopalplus.utils.MyDialog.DialogInterface;
import com.bhopalplus.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactPsychologistActivity extends AppCompatActivity {
    ActivityContactPsychologistBinding binding;
    String getUserToken = "";
    List<ContactPsychologistData> psychologistList = new ArrayList<>();
    PsychologistAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactPsychologistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getUserToken = SharedHelper.getKey(ContactPsychologistActivity.this, AppConstats.USER_TOKEN);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactPsychologistActivity.this, MainActivity.class));
                finish();

            }
        });
        binding.rvContact.setLayoutManager(new LinearLayoutManager(ContactPsychologistActivity.this));
        layoutManager = new LinearLayoutManager(ContactPsychologistActivity.this, LinearLayoutManager.VERTICAL, false);
        psychologistData();
    }

    private void psychologistData() {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ContactPsychologistActivity.this);
        Call<ContactpsychlogistModel> call = APIClient.getAPIClient().contactpsychlogist("Bearer " + getUserToken);
        call.enqueue(new Callback<ContactpsychlogistModel>() {
            @Override
            public void onResponse(Call<ContactpsychlogistModel> call, Response<ContactpsychlogistModel> response) {
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();


                } else {
                    ContactpsychlogistModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            psychologistList = new ArrayList<>();
                            List<ContactpsychlogistModel.Datum> dataList = model.getData();
                            for (ContactpsychlogistModel.Datum data : dataList) {
                                ContactPsychologistData itemData = new ContactPsychologistData();
                                for (int i = 0; i < dataList.size(); i++) {
                                    itemData.setName(data.getName());
                                    itemData.setAddress(data.getAddress());
                                    itemData.setCode(data.getPhoneCode());
                                    itemData.setMobile(data.getNumber());

                                }
                                psychologistList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (ContactPsychologistActivity.this != null) {
                                adapter = new PsychologistAdapter(ContactPsychologistActivity.this, psychologistList);
                                binding.rvContact.setAdapter(adapter);
                            }
                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(ContactPsychologistActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<ContactpsychlogistModel> call, Throwable t) {
                Log.e("regrgr", t.getMessage() + "msg");
                dialogInterface.hideDialog();
            }
        });

    }
}
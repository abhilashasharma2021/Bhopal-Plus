package com.bhopalplus.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.AboutUsModel;
import com.bhopalplus.Model.AddDetailsData;
import com.bhopalplus.Model.LoginData;
import com.bhopalplus.Model.ShowProfileModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.activity.LoginActivity;
import com.bhopalplus.databinding.FragmentAboutUsBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.SharedHelper;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutUsFrag extends Fragment {
    FragmentAboutUsBinding binding;
    View view;
    Context context;
    String getUserToken="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentAboutUsBinding.inflate(getLayoutInflater(),container,false);
        view=binding.getRoot();
        context = getActivity();
        getUserToken = SharedHelper.getKey(getActivity(), AppConstats.USER_TOKEN);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        InternetConnectionInterface connectivity = new InternetConnectivity();
        if (connectivity.isConnected(getActivity())) {
            about_Us();

        } else {
            Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void about_Us(){

       Call<AboutUsModel>call=APIClient.getAPIClient().aboutUs("Bearer " + getUserToken);
       call.enqueue(new Callback<AboutUsModel>() {
           @Override
           public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
               AboutUsModel userData = response.body();
               if(userData.getMessage().equals("data not found")){
                   Toast.makeText(getActivity(),"data not found", Toast.LENGTH_SHORT).show();
               }

               else{
                   AboutUsModel.Data aboutUsData=userData.getData();
                   binding.txWeb.setText(aboutUsData.getWebSite());
                   binding.txContact.setText(aboutUsData.getContact());
                   binding.txAddress.setText(aboutUsData.getAddress());
                   binding.txEmail.setText(aboutUsData.getEmail());

               }



           }



           @Override
           public void onFailure(Call<AboutUsModel> call, Throwable t) {

               Log.e("TAG", "onFailure: "+t.getMessage() );
           }
       });


    }

}
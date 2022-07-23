package com.bhopalplus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhopalplus.Model.AboutUsModel;
import com.bhopalplus.Model.AddDetailsData;
import com.bhopalplus.Model.ShowProfileModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.FragmentAboutUsBinding;
import com.bhopalplus.utils.AppConstats;
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
        return view;
    }

    private void about_Us(){

       Call<AboutUsModel>call=APIClient.getAPIClient().aboutUs("Bearer " + getUserToken);
       call.enqueue(new Callback<AboutUsModel>() {
           @Override
           public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
              /* if (!response.isSuccessful()) {
                   Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

               }

               AboutUsModel aboutUslData = response.body();
               if (aboutUslData != null) {
                   if (aboutUslData.getResult()) {

                       binding.txWeb.setText(aboutUslData.getData().getEmail());
                       binding.txContact.setText(aboutUslData.getData().getName());
                       binding.txAddress.setText(aboutUslData.getData().getAddress());
                       binding.txEmail.setText(aboutUslData.getData().getDob());




                   } else {
                       Toast.makeText(getActivity(), aboutUslData.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }

           }*/
           }


           @Override
           public void onFailure(Call<AboutUsModel> call, Throwable t) {

               Log.e("TAG", "onFailure: "+t.getMessage() );
           }
       });


    }

}
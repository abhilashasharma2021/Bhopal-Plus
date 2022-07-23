package com.bhopalplus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bhopalplus.Model.AboutUsModel;
import com.bhopalplus.Model.FeedbackModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.FragmentFeedbackBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.SharedHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedbackFrag extends Fragment {

FragmentFeedbackBinding binding;
Context context;
View view;
String getUserToken="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentFeedbackBinding.inflate(getLayoutInflater(),container,false);
       view=binding.getRoot();
       context=getActivity();
        getUserToken = SharedHelper.getKey(getActivity(), AppConstats.USER_TOKEN);
        return view;




    }

    private void feedback(){

        Call<FeedbackModel> call= APIClient.getAPIClient().feedback("Bearer " + getUserToken);
        call.enqueue(new Callback<FeedbackModel>() {
            @Override
            public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {
              /* if (!response.isSuccessful()) {
                   Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

               }

               FeedbackModel feedbackData = response.body();
               if (feedbackData != null) {
                   if (feedbackData.getResult()) {

                       binding.etComment.setText(feedbackData.getData().getEmail());
                      binding.ratingStar.setText(Float.toString(feedbackData.getData().getRating()));




                   } else {
                       Toast.makeText(getActivity(), aboutUsModel.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }

           }*/
            }


            @Override
            public void onFailure(Call<FeedbackModel> call, Throwable t) {

                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });


    }


}
package com.bhopalplus.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.AboutUsModel;
import com.bhopalplus.Model.FeedbackModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.FragmentFeedbackBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.InternetConnection.InternetConnectionInterface;
import com.bhopalplus.utils.InternetConnection.InternetConnectivity;
import com.bhopalplus.utils.SharedHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedbackFrag extends Fragment {

    FragmentFeedbackBinding binding;
    Context context;
    View view;
    String getUserToken = "",stComment="",strRating="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentFeedbackBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();
        context = getActivity();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });


        getUserToken = SharedHelper.getKey(getActivity(), AppConstats.USER_TOKEN);



        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stComment=binding.etComment.getText().toString();
                strRating= String.valueOf(binding.ratingStar.getRating());
                if (stComment.equals("")){
                    Toast.makeText(getActivity(), "Type your comment here", Toast.LENGTH_SHORT).show();
                }else if (strRating.equals("")){
                    Toast.makeText(getActivity(), "Select your rating", Toast.LENGTH_SHORT).show();
                }else {
                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getActivity())) {
                        feedback();

                    } else {
                        Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        return view;


    }

    private void feedback() {

        Call<FeedbackModel> call = APIClient.getAPIClient().feedback(strRating, stComment, "Bearer " + getUserToken);
        call.enqueue(new Callback<FeedbackModel>() {
            @Override
            public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {

                Log.e("jsckdshcjk", "onResponse: "+response );
                FeedbackModel feedbackData = response.body();
                if (feedbackData.getMessage().equals("something wrong please fill again")) {
                    Toast.makeText(getActivity(), "something wrong please fill again", Toast.LENGTH_SHORT).show();
                }
                
                else {
                   getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedbackFrag()).commit();
                    binding.etComment.setText("");
                    Toast.makeText(getActivity(), "Feedback Send successfully", Toast.LENGTH_SHORT).show();
                    
                }
            }


            @Override
            public void onFailure(Call<FeedbackModel> call, Throwable t) {

                Log.e("rytrytu", "onFailure: " + t.getMessage());
            }
        });


    }
}



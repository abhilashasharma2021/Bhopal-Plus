package com.bhopalplus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bhopalplus.R;
import com.bhopalplus.databinding.FragmentFeedbackBinding;


public class FeedbackFrag extends Fragment {

FragmentFeedbackBinding binding;
Context context;
View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentFeedbackBinding.inflate(getLayoutInflater(),container,false);
       view=binding.getRoot();
       context=getActivity();
        return view;




    }

}
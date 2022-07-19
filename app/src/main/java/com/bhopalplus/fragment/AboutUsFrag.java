package com.bhopalplus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhopalplus.R;
import com.bhopalplus.databinding.FragmentAboutUsBinding;


public class AboutUsFrag extends Fragment {
    FragmentAboutUsBinding binding;
    View view;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentAboutUsBinding.inflate(getLayoutInflater(),container,false);
        view=binding.getRoot();
        context = getActivity();
        return view;
    }

}
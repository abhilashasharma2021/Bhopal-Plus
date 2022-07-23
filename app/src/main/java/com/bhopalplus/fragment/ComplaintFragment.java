package com.bhopalplus.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhopalplus.R;
import com.bhopalplus.databinding.FragmentComplaintBinding;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ComplaintFragment extends Fragment {
    FragmentComplaintBinding binding;
    View view;
    Context context;
    public static final int FILE_IMAGE_REQUEST_CODE = 1111;
    public static final int FILE_VIDEO_REQUEST_CODE = 8989;
    String filePath = "";
    ArrayList<File> fileList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentComplaintBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();
        context = getActivity();


        /*type 0= TEXT, 1=IMAGE, 2=VIDEO*/
        binding.btSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // strDescription = etDescription.getText().toString().trim();

          //      int selectRadio = radioGroup.getCheckedRadioButtonId();
               // fileRadioButton = findViewById(selectRadio);

             /*   if (selectRadio == -1) {
                    Toast.makeText(getActivity(), "Select one from the above", Toast.LENGTH_SHORT).show();
                } else {

                    if (fileRadioButton.getText().equals("Attach Image")) {

                        Intent intent = new Intent(getContext(), FilePickerActivity.class);
                        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                .setCheckPermission(true)
                                .setShowImages(true)
                                .setShowVideos(false)
                                .enableImageCapture(true)
                                .setMaxSelection(2)
                                .setSkipZeroSizeFiles(true)
                                .build());
                        startActivityForResult(intent, FILE_IMAGE_REQUEST_CODE);

                        // Toast.makeText(UploadFileNewPostActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    } else if (fileRadioButton.getText().equals("Attach Video")) {
                        Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                .setCheckPermission(true)
                                .setShowImages(false)
                                .setShowVideos(true)
                                .enableImageCapture(true)
                                .setMaxSelection(1)
                                .setSkipZeroSizeFiles(true)
                                .build());
                        startActivityForResult(intent, FILE_VIDEO_REQUEST_CODE);

                        // Toast.makeText(UploadFileActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    } else if (fileRadioButton.getText().equals("Text")) {

                      //  text_Post("0", strDescription);
                        //    Toast.makeText(UploadFileNewPostActivity.this, "Text", Toast.LENGTH_SHORT).show();
                    }


                }*/


            }
        });

        return view;



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

            Log.e("ashjkgdfhsh", mediaFiles+"");

            if (mediaFiles != null) {

                for (int i = 0; i < mediaFiles.size(); i++) {
                    Log.e("cscj", String.valueOf(mediaFiles.get(i).getPath()));

                    filePath = mediaFiles.get(i).getPath();

                    Log.e("fncjdshfckj", filePath);

                    fileList.add(new File(filePath));

                  /*  btnADD.setText("POST SUCCESSFULLY ADDED");
                    lottie.setVisibility(View.VISIBLE);*/


                }

                for (int i = 0; i < fileList.size(); i++) {

                    Log.e("skasksasasa", fileList.get(i).toString());
                }

             //   uploadFiles(fileList, "1", strDescription);

            } else {
              /*  btnADD.setText("ADD FILE");
                lottie.setVisibility(View.GONE);*/
            }
        }


        //////VIDEO/////////


        if (requestCode == FILE_VIDEO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            Log.e("fdnhfgnjfgn", mediaFiles+"");

            if (mediaFiles != null) {

                for (int i = 0; i < mediaFiles.size(); i++) {

                    filePath = mediaFiles.get(i).getPath();

                    Log.e("retgrgerfg", filePath);

                    fileList.add(new File(filePath));

                    /*btnADD.setText("POST SUCCESSFULLY ADDED");
                    lottie.setVisibility(View.VISIBLE);*/


                }
                if (fileList.size()>0){

                   // uploadFiles(fileList, "2", strDescription);
                }



            } else {
              /*  btnADD.setText("ADD FILE");
                lottie.setVisibility(View.GONE);*/
            }
        }



    }
}



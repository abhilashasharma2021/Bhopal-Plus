package com.bhopalplus.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhopalplus.R;
import com.bhopalplus.databinding.FragmentComplaintBinding;
import com.bhopalplus.utils.ImageUtils;
import com.bumptech.glide.Glide;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import iam.thevoid.mediapicker.rxmediapicker.Purpose;
import iam.thevoid.mediapicker.rxmediapicker.RxMediaPicker;

public class ComplaintFragment extends Fragment {
    FragmentComplaintBinding binding;
    View view;
    Context context;
    public static final int FILE_VIDEO_REQUEST_CODE = 8989;
    String filePath = "",strDescription="";
    ArrayList<File> fileList = new ArrayList<>();
    private File gallery_file;
    String frontp = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentComplaintBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();
        context = getActivity();
        binding.cdPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* RxMediaPicker.builder(getActivity())
                        .pick(Purpose.Pick.IMAGE)
                        .take(Purpose.Take.PHOTO)
                        .build()
                        .subscribe(filepath -> {
                            Bitmap bitmap = ImageUtils.imageCompress(ImageUtils.getRealPath(getActivity(), filepath));
                            gallery_file = ImageUtils.bitmapToFile(bitmap, getActivity());
                            Glide.with(getActivity()).load(gallery_file).into(binding.ivPhoto);
                            frontp = gallery_file.toString();

                        });*/


            }
        });

        binding.cdVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Log.e("TAG", "onClick: ");
                Intent intent = new Intent(getContext(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(false)
                        .setShowVideos(true)
                        .enableImageCapture(true)
                        .setMaxSelection(1)
                        .setSkipZeroSizeFiles(true)
                        .build());
                startActivityForResult(intent, FILE_VIDEO_REQUEST_CODE);

*/



            }
        });



        return view;



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //////VIDEO/////////


        if (requestCode == FILE_VIDEO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            Log.e("fdnhfgnjfgn", mediaFiles+"");

            if (mediaFiles != null) {

                for (int i = 0; i < mediaFiles.size(); i++) {

                    filePath = mediaFiles.get(i).getPath();

                    Log.e("retgrgerfg", filePath);

                    fileList.add(new File(filePath));

                    Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                   binding.ivVideo.setImageBitmap(thumbnail);


                }

                if (fileList.size()>0){
                    binding.btSent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            strDescription = binding.etDescription.getText().toString().trim();

                            // uploadFiles(fileList, strDescription);
                        }
                    });


                }



            }
        }



    }
}



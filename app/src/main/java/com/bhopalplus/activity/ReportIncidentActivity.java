package com.bhopalplus.activity;

import static com.bhopalplus.fragment.ComplaintFragment.FILE_VIDEO_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.CategoryModel;
import com.bhopalplus.Model.ReportIncidentModel;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityReportIncidentBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.ImageUtils;
import com.bhopalplus.utils.SharedHelper;
import com.bumptech.glide.Glide;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import iam.thevoid.mediapicker.rxmediapicker.Purpose;
import iam.thevoid.mediapicker.rxmediapicker.RxMediaPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportIncidentActivity extends AppCompatActivity {
    ActivityReportIncidentBinding binding;
    String strCategory = "", getUserToken = "";
    ArrayList<String> catId, catName;
    private File gallery_file;
    String filePath = "";
    String strDescription = "";
    ArrayList<File> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportIncidentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserToken = SharedHelper.getKey(ReportIncidentActivity.this, AppConstats.USER_TOKEN);

        Log.e("kdjclkdkj", "onCreate: " + getUserToken);


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportIncidentActivity.this, MainActivity.class));
                finish();

            }
        });


        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strCategory = catId.get(i);
/*  ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.cdPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxMediaPicker.builder(ReportIncidentActivity.this)
                        .pick(Purpose.Pick.IMAGE)
                        .take(Purpose.Take.PHOTO)
                        .build()
                        .subscribe(filepath -> {
                            Bitmap bitmap = ImageUtils.imageCompress(ImageUtils.getRealPath(ReportIncidentActivity.this, filepath));
                            gallery_file = ImageUtils.bitmapToFile(bitmap, ReportIncidentActivity.this);
                            Glide.with(ReportIncidentActivity.this).load(gallery_file).into(binding.ivPhoto);
                            filePath = gallery_file.toString();

                            binding.btSent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    strDescription = binding.etDescription.getText().toString().trim();

                                    if (TextUtils.isEmpty(strDescription)) {
                                        binding.etDescription.setError("Incident Must Required!");
                                        binding.etDescription.requestFocus();
                                    } else if (strCategory.isEmpty()){
                                        Toast.makeText(ReportIncidentActivity.this, "Please choose requires category", Toast.LENGTH_SHORT).show();
                                    }else {
                                        uploadFiles("1", strCategory, strDescription);
                                    }

                                }
                            });


                        });


            }
        });

        binding.cdVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("TAG", "onClick: ");
                Intent intent = new Intent(ReportIncidentActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(false)
                        .setShowVideos(true)
                        .enableImageCapture(true)
                        .setMaxSelection(1)
                        .setSkipZeroSizeFiles(true)
                        .build());
                startActivityForResult(intent, FILE_VIDEO_REQUEST_CODE);


            }
        });


        showCategory();
    }


    public void showCategory() {

        Call<CategoryModel> call = APIClient.getAPIClient().showCategory("Bearer " + getUserToken);
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoryModel> call, @NonNull Response<CategoryModel> response) {
                Log.e("djchdhch", "onResponse: " + response);

                if (!response.isSuccessful()) {

                    Toast.makeText(ReportIncidentActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }


                CategoryModel categoryModel = response.body();
                if (categoryModel != null) {

                    if (categoryModel.getResult()) {

                        catId = new ArrayList<>();
                        catName = new ArrayList<>();
                        catId.add("");
                        catName.add("Select Category");
                        List<CategoryModel.Datum> dataList = categoryModel.getData();
                        for (CategoryModel.Datum data : dataList) {

                            catId.add(String.valueOf(data.getId()));
                            catName.add(data.getName());

                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ReportIncidentActivity.this, android.R.layout.simple_list_item_1, catName);
                        binding.spCategory.setAdapter(arrayAdapter);

                    } else {
                        Toast.makeText(ReportIncidentActivity.this, categoryModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<CategoryModel> call, @NonNull Throwable t) {
                Log.e("shdjshh", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //////VIDEO/////////


        if (requestCode == FILE_VIDEO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            Log.e("fdnhfgnjfgn", mediaFiles + "");

            if (mediaFiles != null) {

                for (int i = 0; i < mediaFiles.size(); i++) {

                    filePath = mediaFiles.get(i).getPath();
                    gallery_file = new File(filePath);

                    Log.e("retgrgerfg", filePath);

                    fileList.add(new File(filePath));

                    Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    binding.ivVideo.setImageBitmap(thumbnail);


                }

                if (fileList.size() > 0) {
                    binding.btSent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            strDescription = binding.etDescription.getText().toString().trim();
                            if (TextUtils.isEmpty(strDescription)) {
                                binding.etDescription.setError("Incident Must Required!");
                                binding.etDescription.requestFocus();
                            } else if (strCategory.isEmpty()){
                                Toast.makeText(ReportIncidentActivity.this, "Please choose requires category!", Toast.LENGTH_SHORT).show();
                            }else {
                                uploadFiles("2", strCategory, strDescription);
                            }


                        }
                    });


                }


            }
        }


    }


    private void uploadFiles(String stType, String strCategory, String strDescription) {


        File file = new File(filePath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);


        Call<ReportIncidentModel> call = APIClient.getAPIClient().reportIncident(stType, strCategory, strDescription, body, "Bearer " + getUserToken);
        call.enqueue(new Callback<ReportIncidentModel>() {
            @Override
            public void onResponse(@NonNull Call<ReportIncidentModel> call, @NonNull Response<ReportIncidentModel> response) {
                Log.e("regregrtg", response.toString());

                ReportIncidentModel incidentModel = response.body();
                if (incidentModel.getMessage().equals("something wrong please fill again")) {
                    Toast.makeText(ReportIncidentActivity.this, incidentModel.getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    startActivity(new Intent(ReportIncidentActivity.this, ReportIncidentActivity.class));
                    Toast.makeText(ReportIncidentActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(@NonNull Call<ReportIncidentModel> call, @NonNull Throwable t) {
                Log.e("frgfdgfd", t.getMessage(), t);
            }
        });
    }






    /*private void uploadFiles(String stType, String strCategory, String strDescription){


        Log.e("dshjd", "gallery_file: "+ gallery_file);
        Log.e("dshjd", "stType: "+ stType);
        Log.e("dshjd", "strCategory: "+ strCategory);
        Log.e("dshjd", "strDescription: "+ strDescription);
        Log.e("dshjd", "getUserToken: "+ getUserToken);


        AndroidNetworking.upload("http://128.199.176.121/bhopalplusbnest/api/report-incident")
                .addMultipartFile("image",gallery_file)
                .addMultipartParameter("type", stType)
                .addMultipartParameter("category", strCategory)
                .addMultipartParameter("description", strDescription)
                .addHeaders("Authorization","Bearer "+ getUserToken)
                .setTag("addPost")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("checkffff", "onResponse: "+response.toString() );
                        try {
                            if (response.getString("result").equals("true")) {

                                startActivity(new Intent(ReportIncidentActivity.this, OtpVerifyActivity.class));
                            }
                            else {


                                Toast.makeText(ReportIncidentActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                            }


                        }
                        catch (JSONException e) {
                            Log.e("gfggggfg", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dsgvfdgv", "onResponse: "+anError.getMessage() );
                    }
                });

    }*/
}






package com.bhopalplus.Retrofit;

import com.bhopalplus.Model.AboutUsModel;
import com.bhopalplus.Model.AddDetailsData;
import com.bhopalplus.Model.FeedbackModel;
import com.bhopalplus.Model.HomeItemModel;
import com.bhopalplus.Model.LoginData;
import com.bhopalplus.Model.OtpVerifyData;
import com.bhopalplus.Model.ResendOtpData;
import com.bhopalplus.Model.ShowProfileModel;
import com.bhopalplus.Model.SignupData;
import com.bhopalplus.Model.SliderModel;
import com.bhopalplus.Model.UpdateProfileModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonInterface {
    @FormUrlEncoded
    @POST(API.signUp)
    Call<SignupData>userRegistration(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.login)
    Call<LoginData>userLogin(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.addUserDetails)
    Call<AddDetailsData>addDetails(@FieldMap Map<String, String> params);



    @FormUrlEncoded
    @POST(API.otpVerify)
    Call<OtpVerifyData> otpVerify(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(API.resendOtp)
    Call<ResendOtpData>resendOtp(@FieldMap Map<String, String> params);

    @GET(API.showServices)
   // @Headers("Authorization: Bearer: $Constants.TOKEN")
    Call<HomeItemModel> showServicesItem(@Header("Authorization")String token);


    @GET(API.showProfile)
    Call<ShowProfileModel> showProfile(@Header("Authorization")String token);


    @POST(API.updateProfile)
    Call<UpdateProfileModel>updateProfile(@Query("id") String id,@Query("name") String name,@Query("email")String email,
                                          @Query("address") String address,@Query("dob")String dob,
                                          @Query("gender") String gender,
                                          @Header("Authorization") String token);


    @GET(API.showBanner)
    Call<SliderModel> showBanner(@Header("Authorization")String token);


    @GET(API.aboutUS)
    Call<AboutUsModel> aboutUs(@Header("Authorization")String token);

    @POST(API.feedback)
    Call<FeedbackModel> feedback(@Query("star") String star,@Query("feedback") String feedback,
            @Header("Authorization")String token);

}

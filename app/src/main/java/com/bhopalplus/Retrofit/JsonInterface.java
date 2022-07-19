package com.bhopalplus.Retrofit;

import com.bhopalplus.Model.AddDetailsData;
import com.bhopalplus.Model.HomeItemModel;
import com.bhopalplus.Model.LoginData;
import com.bhopalplus.Model.OtpVerifyData;
import com.bhopalplus.Model.ResendOtpData;
import com.bhopalplus.Model.SignupData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    Call<HomeItemModel> showServicesItem();



}

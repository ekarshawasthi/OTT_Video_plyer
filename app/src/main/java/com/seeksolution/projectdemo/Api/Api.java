package com.seeksolution.projectdemo.Api;

import com.seeksolution.projectdemo.Model.CreateUserResponse;
import com.seeksolution.projectdemo.Model.LoginResponse;
import com.seeksolution.projectdemo.Model.PackageResponse;
import com.seeksolution.projectdemo.Model.SliderResponse;
import com.seeksolution.projectdemo.Model.UpdatePackageResponse;
import com.seeksolution.projectdemo.Model.VideoModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("user")
    Call<CreateUserResponse> createUser(
      @Field("name") String name,
      @Field("email") String email,
      @Field("password") String password,
      @Field("mobile") String mobile
    );


    @GET("package")
    Call<PackageResponse> getSubcriptionPackages();

    @FormUrlEncoded
    @POST("user")
    Call<UpdatePackageResponse> subscribeToPackage(
            @Field("_method") String _method,
            @Field("user_id") String user_id,
            @Field("package_id") String package_id
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> createLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("banner")
    Call<SliderResponse> getSliderImages();

    @GET("vedios")
    Call<VideoModelResponse> getMoviesData();

//    @FormUrlEncoded
//    @POST("package")
//    Call<PackageResponse> createPackage(
//      @Field("package_name") String package_name,
//      @Field("package_price") String package_price,
//      @Field("package_duration") String package_duration,
//      @Field("package_desc") String package_desc,
//      @Field("package_pic") String package_pic
//    );

}

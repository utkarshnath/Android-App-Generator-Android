package com.developer.sparsh.baseapplication.Interface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by utkarshnath on 15/01/17.
 */

public interface FileUpload {
    @Multipart
    @POST("post/")
    Call<ResponseBody> upload(@Part("appId") RequestBody appId,
                              @Part("userId") RequestBody userId,
                              @Part("timestamp") RequestBody created_at,
                              @Part("description") RequestBody discription,
                              @Part("mimeType") RequestBody type,
                              @Part MultipartBody.Part file);

}

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
    @POST("/api/v1/post/")
    Call<ResponseBody> upload(@Part("uploaded_by") RequestBody uploaded_by,
                              @Part("created_at") RequestBody created_at,
                              @Part("discription") RequestBody discription,
                              @Part("type") RequestBody type,
                              @Part MultipartBody.Part file);

}

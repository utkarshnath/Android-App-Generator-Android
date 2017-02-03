package com.developer.sparsh.baseapplication.Fragments;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.sparsh.baseapplication.Adapters.FeedAdapter;
import com.developer.sparsh.baseapplication.Adapters.FeedCursorAdapter;
import com.developer.sparsh.baseapplication.Helpers.DatabaseContract;
import com.developer.sparsh.baseapplication.Helpers.DatabaseHelper;
import com.developer.sparsh.baseapplication.Interface.FileUpload;
import com.developer.sparsh.baseapplication.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feed_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private File photoFile = null;
    private String TAG = "!@#";
    private static final int SELECT_PICTURE = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    private static String USER_POST_URL = "http://223.179.128.215/api/v1/";
    private RecyclerView feed_recyclerview;
    private FeedCursorAdapter mAdapter = null;
    private RequestQueue queue;
    private String GET_POST_URL = "http://223.179.128.215/api/v1/post/?appId=";
    private String GET_INVITEES_URL = "";
    private DatabaseHelper helper;
    private ThinDownloadManager downloadManager;

    public Feed_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_layout_feed, container, false);
        setUpFloatingActionMenu(view);
        downloadManager = new ThinDownloadManager();

        queue = Volley.newRequestQueue(getActivity());
        helper = new DatabaseHelper(getActivity());
        feed_recyclerview = (RecyclerView) view.findViewById(R.id.feed_recycler_view);
        feed_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FeedCursorAdapter(getContext() , null);
        feed_recyclerview.setAdapter(mAdapter);
        downloadEveryThing(1);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(100,null,this);
    }

    private void setUpFloatingActionMenu(View v) {

        com.getbase.floatingactionbutton.FloatingActionButton galleryImageAction =
                (com.getbase.floatingactionbutton.FloatingActionButton)v.findViewById(R.id.gallery_image_action);
        galleryImageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPhoto();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton galleryVideoAction =
                (com.getbase.floatingactionbutton.FloatingActionButton)v.findViewById(R.id.gallery_video_action);
        galleryVideoAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickVideo();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton cameraImageAction =
                (com.getbase.floatingactionbutton.FloatingActionButton)v.findViewById(R.id.camera_image_action);
        cameraImageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton cameraVideoAction =
                (com.getbase.floatingactionbutton.FloatingActionButton)v.findViewById(R.id.camera_video_action);
        cameraVideoAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });
    }

    // ************************************** HANDLING IMAGES *****************************************

    public void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String appName = getString(R.string.app_name);
        File storageDir = getActivity().getExternalFilesDir(Environment.getExternalStorageDirectory()+"/"+appName+"/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public String getImagePath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }


    // ******************************************** HANDLING VIDEOS *************************************************************

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }

    }

    private void pickVideo(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Video"), REQUEST_VIDEO_CAPTURE);
    }

    public String getVideoPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    // *********************************************************************************************************


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            String imageString = getImagePath(imageUri);
            uploadFile(imageString);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Uri imageUri = Uri.fromFile(photoFile);
            uploadFile(imageUri.getPath());
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            String videopath = getVideoPath(videoUri);
            uploadFile(videopath);
        }
    }

    private void uploadFile(String path) {
        FileUpload service = new Retrofit.Builder().baseUrl(USER_POST_URL).build().create(FileUpload.class);
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("postData", file.getName(), requestFile);
        // DUMMY DATA
        String tittle = "hello, this is description speaking";
        String type = "image";
        long createdAt = System.currentTimeMillis()/1000;
        String userId = "587b2fff673c3a251c3478fe";
        String appId = "588c9af02561712a0c1e93cb";
        RequestBody post_type = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(type));
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), tittle);
        RequestBody appid = RequestBody.create(MediaType.parse("multipart/form-data"), appId);
        RequestBody created_timestamp = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(createdAt));
        RequestBody uploader_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        Call<ResponseBody> call = service.upload(uploader_id,appid,created_timestamp,description,post_type,body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    // ***************************************************** FETCHING EVERY THING FROM SERVER **********************************

    void downloadEveryThing(int limit){
        try {
            downloadPost(limit);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //downloadInvitees();
        //download event details
    }

    private void downloadPost(int limit) throws UnsupportedEncodingException {
        String appId = "587b2fff673c3a251c3478fe";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_POST_URL+ URLEncoder.encode(appId, "UTF-8"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("!@#","resposnse "+ response);
                        try {
                            JSONArray postArray = new JSONArray(response);
                            for(int i=0;i<postArray.length();i++){
                                JSONObject postObject = postArray.getJSONObject(i);
                                long timeStamp = postObject.getLong("timestamp");
                                String mineType = postObject.getString("mimeType");
                                String userId = postObject.getString("userId");
                                String locationUri = postObject.getString("locationUri");
                                String description = postObject.getString("description");
                                String postId = postObject.getString("_id");

                                JSONArray likesArray = postObject.getJSONArray("likes");
                                for(int j=0;j<likesArray.length();j++){
                                    JSONObject likesObject = likesArray.getJSONObject(j);
                                    String likesUserId = likesObject.getString("userId");
                                    String likesId = likesObject.getString("_id");

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(helper.Like_ID,likesId);
                                    contentValues.put(helper.Likes_By_Invitee_Id,likesUserId);
                                    contentValues.put(helper.Likes_Post_Id,postId);
                                    getActivity().getContentResolver().insert(DatabaseContract.LIKES_CONTENT_URI,contentValues);
                                }

                                JSONArray commentsArray = postObject.getJSONArray("comments");
                                for(int k=0;i<commentsArray.length();k++){
                                    JSONObject commentsObject = commentsArray.getJSONObject(k);
                                    long commentTimeStamp = commentsObject.getLong("timeStamp");
                                    String commentUserId = commentsObject.getString("userId");
                                    String commentDescription = commentsObject.getString("description");
                                    String commentId = commentsObject.getString("_id");

                                        JSONArray commentsLikesArray = commentsObject.getJSONArray("likes");
                                        for(int l=0;l<commentsLikesArray.length();l++){
                                            JSONObject commentsLikesObject = commentsLikesArray.getJSONObject(l);
                                            String commentsLikesUserId = commentsLikesObject.getString("userId");
                                            String likesId = commentsLikesObject.getString("_id");

                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(helper.Comment_Like_ID,likesId);
                                            contentValues.put(helper.Comments_Likes_By_Invitee_Id,commentsLikesUserId);
                                            contentValues.put(helper.Likes_Comment_Id,commentId);
                                            getActivity().getContentResolver().insert(DatabaseContract.COMMENTS_LIKES_CONTENT_URI,contentValues);
                                        }

                                        JSONArray commentsRepliesArray = commentsObject.getJSONArray("replies");
                                        for(int m=0;i<commentsRepliesArray.length();m++) {
                                            JSONObject repliesObject = commentsRepliesArray.getJSONObject(m);
                                            String repliesUserId = repliesObject.getString("userId");
                                            String repliesId = repliesObject.getString("_id");
                                            String repliesDescription = repliesObject.getString("replies");
                                            long repliesTimeStamp = repliesObject.getLong("timestamp");

                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(helper.Replies_TimeStamp, repliesTimeStamp);
                                            contentValues.put(helper.Comment_Replies_ID, repliesId);
                                            contentValues.put(helper.Comments_Reply, repliesDescription);
                                            contentValues.put(helper.Replies_Comment_Id, commentId);
                                            contentValues.put(helper.Comment_By_Invitee_Id, repliesUserId);
                                            getActivity().getContentResolver().insert(DatabaseContract.COMMENTS_REPLIES_CONTENT_URI, contentValues);
                                        }

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(helper.Comment_ID,commentId);
                                    contentValues.put(helper.Comment_By_Invitee_Id,commentUserId);
                                    contentValues.put(helper.Comment_Discription,commentDescription);
                                    contentValues.put(helper.Comment_TimeStamp,commentTimeStamp);
                                    contentValues.put(helper.Comment_Post_Id,postId);
                                    getActivity().getContentResolver().insert(DatabaseContract.COMMENT_CONTENT_URI,contentValues);
                                }

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(helper.Post_By_Invitee_Id,userId);
                                contentValues.put(helper.Post_Discription,description);
                                contentValues.put(helper.Post_TimeStamp,timeStamp);
                                contentValues.put(helper.Post_File_Url,locationUri);
                                contentValues.put(helper.Post_Type,mineType);
                                contentValues.put(helper.Post_ID,postId);
//                                if(mineType=="image"){
//                                    //imageDownload(getContext(),locationUri,postId);
//                                }else {
//                                    //videoDownload(locationUri,postId);
//                                }
                                getActivity().getContentResolver().insert(DatabaseContract.POST_CONTENT_URI,contentValues);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.d("!@#",error.toString());
            }
        });
        queue.add(stringRequest);
    }


    void downloadInvitees(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_INVITEES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray inviteesArray = new JSONArray(response);
                            for(int i=0;i<inviteesArray.length();i++){
                                JSONObject inviteeObject = inviteesArray.getJSONObject(i);
                                String userId = inviteeObject.getString("userId");
                                String name = inviteeObject.getString("name");
                                String dpLink = inviteeObject.getString("dpLink");
                                Boolean going = inviteeObject.getBoolean("going");

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(helper.Invitee_Dp,dpLink);
                                contentValues.put(helper.Invitee_Going,going);
                                contentValues.put(helper.Invitee_Name,name);
                                contentValues.put(helper.User_Id,userId);
                                getActivity().getContentResolver().insert(DatabaseContract.INVITEE_CONTENT_URI,contentValues);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


     void videoDownload(String downloadUrl, final String postId){
        Uri destinationUri = Uri.parse(Environment.getExternalStorageDirectory().getPath() +
                "/" + R.string.app_name + "/"+"videos/" + postId + ".mp4");
        Uri downloadUri = Uri.parse(downloadUrl);
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        ContentValues contentValues = new ContentValues();
                        String path = Environment.getExternalStorageDirectory().getPath()+"/"+R.string.app_name+
                                "/videos/"+postId+".mp4";
                        contentValues.put(helper.Post_File_Url,path);
                        getActivity().getContentResolver().update(DatabaseContract.POST_CONTENT_URI,contentValues,helper.Post_ID, new String[]{postId});
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {

                    }
                });
        int downloadId = downloadManager.add(downloadRequest);
    }

    // *************************************************************************************************************************

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext() ,
                DatabaseContract.POST_CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // TODO Swap cursor
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}

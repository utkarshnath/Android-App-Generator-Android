package com.developer.sparsh.baseapplication.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by utkarshnath on 03/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "MyDatabase";

    public static final String Invitees_Table = "InviteesTable";
    public static final String User_Id = "UserId";
    public static final String Invitee_Going = "InviteeGoing";
    public static final String Invitee_Name = "InviteeName";
    public static final String Invitee_Dp = "Invitee_Dp";

    public static final String Post_Table = "PostTable";
    public static final String Post_By_Invitee_Id = "PostByInviteeId";
    public static final String Post_Discription = "PostDiscription";
    public static final String Post_Type = "PostType";
    public static final String Post_Image_Url = "PostImageUrl";
    public static final String Post_Video_Url = "PostVideoUrl";

    public static final String Comments_Table = "CommentsTable";
    public static final String Comment_By_Invitee_Id = "CommentByInviteeId";
    public static final String Comment_Post_Id = "CommentPostId";
    public static final String Comment_Discription = "CommentDiscription";

    public static final String Likes_Table = "LikesTable";
    public static final String Likes_Post_Id = "LikesPostId";
    public static final String Likes_By_Invitee_Id = "LikesByInviteeId";

    public static final String Comments_Likes_Table = "CommentsLikesTable";
    public static final String Comments_Comment_Id = "CommentsCommentId";

    public static final String Comments_Replies_Table = "CommentsRepliesTables";
    public static final String Comments_Reply = "CommentsReply";

    public static final String UID = "_id";
    public static int version = 1;

    public DatabaseHelper(Context context) {
        super(context,Database_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "  + Invitees_Table +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                User_Id + " VARCHAR(255)," +
                Invitee_Going + " VARCHAR(255)," +
                Invitee_Name + " FLOAT," +
                Invitee_Dp + " FLOAT,"+" UNIQUE ("+User_Id+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Post_Table +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Post_By_Invitee_Id + " VARCHAR(255)," +
                Post_Discription + " VARCHAR(255)," +
                Post_Type + " FLOAT," +
                Post_Video_Url + " FLOAT," +
                Post_Image_Url + " FLOAT,"+" UNIQUE ("+UID+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Comments_Table +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Comment_By_Invitee_Id + " VARCHAR(255)," +
                Comment_Post_Id + " VARCHAR(255)," +
                Comment_Discription + " FLOAT,"+" UNIQUE ("+UID+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Likes_Table +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Likes_Post_Id + " VARCHAR(255)," +
                Likes_By_Invitee_Id + " FLOAT,"+" UNIQUE ("+Likes_Post_Id+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Comments_Likes_Table +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Likes_By_Invitee_Id + " VARCHAR(255)," +
                Comments_Comment_Id + " FLOAT,"+" UNIQUE ("+UID+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Comments_Replies_Table +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Comment_By_Invitee_Id + " VARCHAR(255)," +
                Comments_Comment_Id + " VARCHAR(255)," +
                Comments_Reply + " FLOAT,"+" UNIQUE ("+UID+") ON CONFLICT REPLACE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

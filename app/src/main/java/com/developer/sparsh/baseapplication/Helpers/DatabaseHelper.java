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
    public static final String Post_ID = "Post_id";
    public static final String Post_By_Invitee_Id = "PostByInviteeId";
    public static final String Post_Discription = "PostDiscription";
    public static final String Post_Type = "PostType";
    public static final String Post_File_Url = "PostFileUrl";
    public static final String Post_TimeStamp = "PostTimeStamp";

    public static final String Comments_Table = "CommentsTable";
    public static final String Comment_ID = "Comment_id";
    public static final String Comment_By_Invitee_Id = "CommentByInviteeId";
    public static final String Comment_Post_Id = "CommentPostId";
    public static final String Comment_Discription = "CommentDiscription";
    public static final String Comment_TimeStamp = "CommentTimeStamp";

    public static final String Likes_Table = "LikesTable";
    public static final String Like_ID = "Like_id";
    public static final String Likes_Post_Id = "LikesPostId";
    public static final String Likes_By_Invitee_Id = "LikesByInviteeId";

    public static final String Comments_Likes_Table = "CommentsLikesTable";
    public static final String Comment_Like_ID = "Comment_like_id";
    public static final String Comments_Likes_By_Invitee_Id = "LikesByInviteeId";
    public static final String Likes_Comment_Id = "LikesCommentId";

    public static final String Comments_Replies_Table = "CommentsRepliesTables";
    public static final String Comment_Replies_ID = "Comment_Replies_id";
    public static final String Comments_Reply = "CommentsReply";
    public static final String Replies_TimeStamp = "RepliesTimeStamp";
    public static final String Replies_Comment_Id = "Replies_Comment_Id";


    public static int version = 1;

    public DatabaseHelper(Context context) {
        super(context,Database_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "  + Invitees_Table +
                " (" + User_Id + " VARCHAR(255)," +
                Invitee_Going + " VARCHAR(255)," +
                Invitee_Name + " VARCHAR(255)," +
                Invitee_Dp + " VARCHAR(255),"+" UNIQUE ("+User_Id+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Post_Table +
                " (" + Post_ID + " VARCHAR(255)," +
                Post_By_Invitee_Id + " VARCHAR(255)," +
                Post_Discription + " VARCHAR(255)," +
                Post_Type + " VARCHAR(255)," +
                Post_TimeStamp + " INTEGER," +
                Post_File_Url + " VARCHAR(255),"+" UNIQUE ("+Post_ID+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Comments_Table +
                " (" + Comment_ID + " VARCHAR(255)," +
                Comment_By_Invitee_Id + " VARCHAR(255)," +
                Comment_Post_Id + " VARCHAR(255)," +
                Comment_TimeStamp + " INTEGER," +
                Comment_Discription + " VARCHAR(255),"+" UNIQUE ("+Comment_ID+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Likes_Table +
                " (" + Like_ID + " VARCHAR(255)," +
                Likes_Post_Id + " VARCHAR(255)," +
                Likes_By_Invitee_Id + " VARCHAR(255),"+" UNIQUE ("+Likes_Post_Id+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Comments_Likes_Table +
                " (" + Comment_Like_ID + " VARCHAR(255)," +
                Comments_Likes_By_Invitee_Id + " VARCHAR(255)," +
                Likes_Comment_Id + " VARCHAR(255),"+" UNIQUE ("+Comment_Like_ID+") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE "  + Comments_Replies_Table +
                " (" + Comment_Replies_ID + " VARCHAR(255)," +
                Comment_By_Invitee_Id + " VARCHAR(255)," +
                Replies_Comment_Id + " VARCHAR(255)," +
                Replies_TimeStamp + " INTEGER," +
                Comments_Reply + " VARCHAR(255),"+" UNIQUE ("+Comment_Replies_ID+") ON CONFLICT REPLACE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

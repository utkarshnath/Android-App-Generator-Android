package com.developer.sparsh.baseapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.runner.AndroidJUnit4;

import com.developer.sparsh.baseapplication.Helpers.DatabaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by SPARSH on 1/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseUnitTest {

    private static SQLiteOpenHelper mHelper;

    @Test
    public void testCreateDb() throws Exception{
        mHelper = new DatabaseHelper(getTargetContext());
        SQLiteDatabase mDatabase = mHelper.getWritableDatabase();

        assertEquals(true , mDatabase.isOpen());
        mDatabase.close();
    }

    // Test for checking whether insertion and deletion works in the database tables
    @Test
    public void testInsertReadDb() throws Exception{
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues postValues = getPostContentValues();
        ContentValues inviteesValues = getInviteesContentValues();
        ContentValues commentsValues = getCommentsContentValues();
        ContentValues likesValues = getLikesContentValues();
        ContentValues commentLikesValues = getCommentsLikesContentValues();
        ContentValues commentRepliesValues = getCommentsRepliesContentValues();

        long _id = db.insert(DatabaseHelper.Post_Table, null , postValues);
        assertNotEquals(_id , -1);

        _id = db.insert(DatabaseHelper.Invitees_Table, null , inviteesValues);
        assertNotEquals(_id,  -1);

        _id = db.insert(DatabaseHelper.Comments_Table, null , commentsValues);
        assertNotEquals(_id,  -1);

        _id = db.insert(DatabaseHelper.Likes_Table, null , likesValues);
        assertNotEquals(_id,  -1);

        _id = db.insert(DatabaseHelper.Comments_Likes_Table, null , commentLikesValues);
        assertNotEquals(_id,  -1);

        _id = db.insert(DatabaseHelper.Comments_Replies_Table, null , commentRepliesValues);
        assertNotEquals(_id,  -1);

        // Now query the database
        Cursor cursor = db.query(DatabaseHelper.Post_Table,null,null,null,null,null,null);
        validateCursor(cursor,postValues);

        cursor = db.query(DatabaseHelper.Invitees_Table,null,null,null,null,null,null);
        validateCursor(cursor,inviteesValues);

        cursor = db.query(DatabaseHelper.Comments_Table,null,null,null,null,null,null);
        validateCursor(cursor,commentsValues);

        cursor = db.query(DatabaseHelper.Likes_Table,null,null,null,null,null,null);
        validateCursor(cursor,likesValues);

        cursor = db.query(DatabaseHelper.Comments_Likes_Table,null,null,null,null,null,null);
        validateCursor(cursor,commentLikesValues);

        cursor = db.query(DatabaseHelper.Comments_Replies_Table,null,null,null,null,null,null);
        validateCursor(cursor,commentRepliesValues);
    }


    private static ContentValues getPostContentValues(){

        String postId  = "jdncskcj1in32jn33jn";
        String postByInviteeId = "3298ru493823932";
        String postDescription = "This is a test post";
        String postType = "image";
        String postFileUrl = "http://www.google.com";
        long timestamp = new Date().getTime();


        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.Post_ID , postId);
        values.put(DatabaseHelper.Post_By_Invitee_Id, postByInviteeId);
        values.put(DatabaseHelper.Post_Discription , postDescription);
        values.put(DatabaseHelper.Post_Type , postType);
        values.put(DatabaseHelper.Post_File_Url , postFileUrl);
        values.put(DatabaseHelper.Post_TimeStamp , timestamp);

        return values;
    }

    private static ContentValues getInviteesContentValues(){
        ContentValues values = new ContentValues();

        String userId = "394324u2934ide";
        String inviteeGoing = "yes";
        String inviteeName = "utkarsh nath";
        String inviteeDp = "file://sdcard/emulated/0/app/file.jpg";

        values.put(DatabaseHelper.User_Id , userId);
        values.put(DatabaseHelper.Invitee_Going , inviteeGoing);
        values.put(DatabaseHelper.Invitee_Dp , inviteeDp);
        values.put(DatabaseHelper.Invitee_Name , inviteeName);

        return values;
    }

    private static ContentValues getCommentsContentValues(){

        ContentValues values = new ContentValues();

        String commentId = "jsdcn23idn2iun";
        String commentByInviteeId = "dwek23id239dj2";
        String postId = "jdncskcj1in32jn33jn";
        long timestamp = new Date().getTime();
        String commentDescription = "this is a test comment";

        values.put(DatabaseHelper.Comment_ID,commentId);
        values.put(DatabaseHelper.Comment_By_Invitee_Id , commentByInviteeId);
        values.put(DatabaseHelper.Comment_Post_Id, postId);
        values.put(DatabaseHelper.Comment_TimeStamp , timestamp);
        values.put(DatabaseHelper.Comment_Discription , commentDescription);

        return values;
    }

    private static ContentValues getLikesContentValues(){

        ContentValues values = new ContentValues();

        String likeId = "jsdcnxsa23idn2iun";
        String likeByInviteeId = "dwek23id239dj2";
        String postId = "jdncskcj1in32jn33jn";


        values.put(DatabaseHelper.Like_ID,likeId);
        values.put(DatabaseHelper.Likes_By_Invitee_Id , likeByInviteeId);
        values.put(DatabaseHelper.Likes_Post_Id, postId);

        return values;
    }

    private static ContentValues getCommentsLikesContentValues(){

        ContentValues values = new ContentValues();

        String commentLikeId = "cdjskc3498nc39";
        String commentLikeByInviteeId = "dwek23id239dj2";
        String likesCommentId = "jsdcn23idn2iun";


        values.put(DatabaseHelper.Comment_Like_ID,commentLikeId);
        values.put(DatabaseHelper.Comments_Likes_By_Invitee_Id , commentLikeByInviteeId);
        values.put(DatabaseHelper.Likes_Comment_Id, likesCommentId);

        return values;
    }

    private static ContentValues getCommentsRepliesContentValues(){

        ContentValues values = new ContentValues();

        String commentReplyId = "cdjskc3498nc39";
        String commentReplyByInviteeId = "dwek23id239dj2";
        String repliesCommentId = "jsdcn23idn2iun";
        String commentsReply = "Testing is boring";
        long timestamp = new Date().getTime();

        values.put(DatabaseHelper.Comment_Replies_ID,commentReplyId);
        values.put(DatabaseHelper.Comment_By_Invitee_Id , commentReplyByInviteeId);
        values.put(DatabaseHelper.Replies_Comment_Id, repliesCommentId);
        values.put(DatabaseHelper.Replies_TimeStamp , timestamp);
        values.put(DatabaseHelper.Comments_Reply , commentsReply);
        return values;
    }

    public void validateCursor(Cursor valueCursor , ContentValues expectedValues){

        assertTrue(valueCursor.moveToFirst());
        Set<Map.Entry<String,Object>> valueSet = expectedValues.valueSet();
        for(Map.Entry<String,Object> entry : valueSet){
            int idx = valueCursor.getColumnIndex(entry.getKey());
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue , valueCursor.getString(idx));
        }
        valueCursor.close();
    }
}

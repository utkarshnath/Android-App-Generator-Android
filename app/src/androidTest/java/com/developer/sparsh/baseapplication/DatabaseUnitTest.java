package com.developer.sparsh.baseapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.developer.sparsh.baseapplication.Helpers.DatabaseContract;
import com.developer.sparsh.baseapplication.Helpers.DatabaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

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

        long _id = db.insert(DatabaseHelper.Post_Table, null , getPostContentValues());
        assertNotEquals(_id , -1);

        _id = db.insert(DatabaseHelper.Invitees_Table, null , getInviteesContentValues());
        assertNotEquals(_id,  -1);

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
}

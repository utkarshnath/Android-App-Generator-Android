package com.developer.sparsh.baseapplication.Helpers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by utkarshnath on 05/01/17.
 */

public class DataProvider extends ContentProvider {
    private DatabaseHelper helper;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private static final String TAG = DataProvider.class.getSimpleName();
    private static final int INVITEE_TABLE = 100;
    private static final int POST_TABLE = 101;
    private static final int COMMENTS_TABLE = 102;
    private static final int LIKES_TABLE = 103;
    private static final int COMMENTS_LIKES_TABLE = 104;
    private static final int COMMENTS_REPLY_TABLE = 105;



    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        final int match = uriMatcher.match(uri);
        switch (match){
            case INVITEE_TABLE:
                cursor = helper.getReadableDatabase().query(
                        DatabaseContract.INVITEE_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case POST_TABLE:
                cursor = helper.getReadableDatabase().query(
                        DatabaseContract.POST_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMMENTS_TABLE:
                cursor = helper.getReadableDatabase().query(
                        DatabaseContract.COMMENT_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case LIKES_TABLE:
                cursor = helper.getReadableDatabase().query(
                        DatabaseContract.LIKES_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMMENTS_LIKES_TABLE:
                cursor = helper.getReadableDatabase().query(
                        DatabaseContract.COMMENTS_LIKES_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMMENTS_REPLY_TABLE:
                cursor = helper.getReadableDatabase().query(
                        DatabaseContract.COMMENTS_REPLIES_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                Log.d(TAG,"No Uri match Found!");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case INVITEE_TABLE:
                return DatabaseContract.INVITEE_CONTENT_TYPE;
            case POST_TABLE:
                return DatabaseContract.POST_CONTENT_TYPE;
            case COMMENTS_TABLE:
                return DatabaseContract.COMMENT_CONTENT_TYPE;
            case LIKES_TABLE:
                return DatabaseContract.LIKES_CONTENT_TYPE;
            case COMMENTS_LIKES_TABLE:
                return DatabaseContract.COMMENTS_LIKES_CONTENT_TYPE;
            case COMMENTS_REPLY_TABLE:
                return DatabaseContract.COMMENTS_REPLIES_CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Uri mismatch");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = uriMatcher.match(uri);
        Uri returnUri = null;
        switch (match){
            case INVITEE_TABLE:
                long idx = helper.getWritableDatabase().insert(DatabaseContract.INVITEE_TABLE_NAME,null,values);
                if(idx > 0){
                    returnUri = uri;
                }
                break;
            case POST_TABLE:
                idx = helper.getWritableDatabase().insert(DatabaseContract.POST_TABLE_NAME,null,values);
                if(idx > 0){
                    returnUri = uri;
                }
                break;
            case COMMENTS_TABLE:
                idx = helper.getWritableDatabase().insert(DatabaseContract.COMMENT_TABLE_NAME,null,values);
                if(idx > 0){
                    returnUri = uri;
                }
                break;
            case LIKES_TABLE:
                idx = helper.getWritableDatabase().insert(DatabaseContract.LIKES_TABLE_NAME,null,values);
                if(idx > 0){
                    returnUri = uri;
                }
                break;
            case COMMENTS_LIKES_TABLE:
                idx = helper.getWritableDatabase().insert(DatabaseContract.COMMENTS_LIKES_TABLE_NAME,null,values);
                if(idx > 0){
                    returnUri = uri;
                }
                break;
            case COMMENTS_REPLY_TABLE:
                idx = helper.getWritableDatabase().insert(DatabaseContract.COMMENTS_REPLIES_TABLE_NAME,null,values);
                if(idx > 0){
                    returnUri = uri;
                }
                break;
            default:
                throw new UnsupportedOperationException("Uri Mismatch");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        int rowsDeleted = 0;
        switch (match){
            case INVITEE_TABLE:
                rowsDeleted= helper.getWritableDatabase().delete(DatabaseContract.INVITEE_TABLE_NAME,selection,selectionArgs);
                break;
            case POST_TABLE:
                rowsDeleted = helper.getWritableDatabase().delete(DatabaseContract.POST_TABLE_NAME,selection,selectionArgs);
                break;
            case COMMENTS_TABLE:
                rowsDeleted = helper.getWritableDatabase().delete(DatabaseContract.COMMENT_TABLE_NAME,selection,selectionArgs);
                break;
            case LIKES_TABLE:
                rowsDeleted = helper.getWritableDatabase().delete(DatabaseContract.LIKES_TABLE_NAME,selection,selectionArgs);
                break;
            case COMMENTS_LIKES_TABLE:
                rowsDeleted = helper.getWritableDatabase().delete(DatabaseContract.COMMENTS_LIKES_TABLE_NAME,selection,selectionArgs);
                break;
            case COMMENTS_REPLY_TABLE:
                rowsDeleted = helper.getWritableDatabase().delete(DatabaseContract.COMMENTS_REPLIES_TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri Mismatch");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        int rowsUpdated = 0;
        switch (match){
            case INVITEE_TABLE:
                rowsUpdated = helper.getWritableDatabase().update(DatabaseContract.INVITEE_TABLE_NAME,values,selection,selectionArgs);
                break;
            case POST_TABLE:
                rowsUpdated = helper.getWritableDatabase().update(DatabaseContract.POST_TABLE_NAME,values,selection,selectionArgs);
                break;
            case COMMENTS_TABLE:
                rowsUpdated = helper.getWritableDatabase().update(DatabaseContract.COMMENT_TABLE_NAME,values,selection,selectionArgs);
                break;
            case LIKES_TABLE:
                rowsUpdated = helper.getWritableDatabase().update(DatabaseContract.LIKES_TABLE_NAME,values,selection,selectionArgs);
                break;
            case COMMENTS_REPLY_TABLE:
                rowsUpdated = helper.getWritableDatabase().update(DatabaseContract.COMMENTS_REPLIES_TABLE_NAME,values,selection,selectionArgs);
                break;
            case COMMENTS_LIKES_TABLE:
                rowsUpdated = helper.getWritableDatabase().update(DatabaseContract.COMMENTS_LIKES_TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri Mismatch");
        }
        if(rowsUpdated>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return 0;
    }

    // Helper method to build the URI MATCHER
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String CONTENT_AUTHORITY = DatabaseContract.CONTENT_AUTHORITY;

        // Add the Content Uri to the matcher
        matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_INVITEE_TABLE, INVITEE_TABLE);
        matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_POST_TABLE, POST_TABLE);
        matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_COMMENTS_TABLE,COMMENTS_TABLE);
        matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_LIKES_TABLE,LIKES_TABLE);
        matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_COMMENTS_LIKES_TABLE,COMMENTS_LIKES_TABLE);
        matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_COMMENTS_REPLY_TABLE,COMMENTS_REPLY_TABLE);
        return matcher;
    }
}

package com.qibei.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/12.
 */

public class TimeEventProvider extends ContentProvider {
    private static final String AUTHORITIES = "com.qibei.timeeventprovider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/timeTable");
    public static final int TIME_EVENT_URI_CODE = 0;
    private static final UriMatcher mUriMather = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMather.addURI(AUTHORITIES, "timeTable", TIME_EVENT_URI_CODE);
    }

    private SQLiteDatabase mDb;
    private Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d("thread","thread:"+Thread.currentThread().getName());
        String tableName=getTableName(uri);
        if(tableName==null){
            throw new IllegalArgumentException("Un supported Uri:"+uri);
        }
        return mDb.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("provider","insert");
        String tableName=getTableName(uri);
        if(tableName==null){
            throw new IllegalArgumentException("Un supported Uri:"+uri);
        }
        mDb.insert(tableName,null,values);
        //注意插入数据库的时候，数据库数据会变化，所以需要通知外界
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName=getTableName(uri);
        if(tableName==null){
            throw new IllegalArgumentException("Un supported Uri:"+uri);
        }
        int count=mDb.delete(tableName,selection,selectionArgs);
        //注意插入数据库的时候，数据库数据会变化，所以需要通知外界
        if(count>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName=getTableName(uri);
        if(tableName==null){
            throw new IllegalArgumentException("Un supported Uri:"+uri);
        }
        int count=mDb.update(tableName,values,selection,selectionArgs);
        //注意插入数据库的时候，数据库数据会变化，所以需要通知外界
        if(count>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }


    //通过Uri查询表名
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMather.match(uri)) {
            case TIME_EVENT_URI_CODE:
                tableName = DbOpenHelper.TIME_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }
}

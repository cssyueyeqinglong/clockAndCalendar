package com.qibei.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/12.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "timeEvent.db";
    public static final String TIME_TABLE_NAME = "timeTable";
    private static final int DB_VERSION = 1;

    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + TIME_TABLE_NAME + "(_id INTEGER PRIVATE KEY," + "timeId INTEGER,"
            + "year Integer," + "month Integer," + "day Integer," + "week Integer,"
            + "featureId Integer," + "timeDesc TEXT," + "place TEXT," + "desc TEXT," + "cardTime Integer" + ")";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.app.mytasks.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mytasks.db";
    public static final String TASKS_TABLE_NAME = "tasks";
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_COLUMN_TITLE = "title";
    public static final String TASK_COLUMN_DETAILS = "details";

    static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TASKS_TABLE_NAME + " (" + TASK_COLUMN_ID + " integer primary key autoincrement, "
            + TASK_COLUMN_TITLE + " text not null, " + TASK_COLUMN_DETAILS + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}

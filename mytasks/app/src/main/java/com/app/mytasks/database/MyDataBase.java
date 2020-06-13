package com.app.mytasks.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.app.mytasks.database.DBHelper.TASKS_TABLE_NAME;
import static com.app.mytasks.database.DBHelper.TASK_COLUMN_DETAILS;
import static com.app.mytasks.database.DBHelper.TASK_COLUMN_ID;
import static com.app.mytasks.database.DBHelper.TASK_COLUMN_TITLE;

public class MyDataBase {

    private static MyDataBase myDataBase;
    private DBHelper dbHelper;

    private MyDataBase(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static MyDataBase getInstance(Context context) {
        if (myDataBase == null)
            myDataBase = new MyDataBase(context);

        return myDataBase;
    }


    public boolean insertTask(TaskModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_TITLE, model.getTitle());
        contentValues.put(TASK_COLUMN_DETAILS, model.getDetails());
        db.insert(TASKS_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }


    public Cursor getTask(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TASKS_TABLE_NAME + " where " + TASK_COLUMN_ID + " = " + id + "", null);
        return res;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM " + TASKS_TABLE_NAME + " WHERE " + TASK_COLUMN_ID + "='" + id + "'");
        db.close();

    }

    public ArrayList<TaskModel> getAllTasks() {
        ArrayList<TaskModel> array_list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TASKS_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            TaskModel model = new TaskModel();
            model.setId(res.getShort(res.getColumnIndex(TASK_COLUMN_ID)));
            model.setTitle(res.getString(res.getColumnIndex(TASK_COLUMN_TITLE)));
            model.setDetails(res.getString(res.getColumnIndex(TASK_COLUMN_DETAILS)));
            array_list.add(model);
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

}

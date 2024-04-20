package com.hav.firstapp.Utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.hav.firstapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoList";
    private static final String TABLE_NAME = "todo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_STATUS = "status";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TASK + " TEXT," + COLUMN_STATUS + " TINYINT)";

    private SQLiteDatabase db;
    public DatabaseHandler(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void openDatabase(){
        db = getWritableDatabase();
    }

    public void insertTask(ToDoModel toDoModel){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK, toDoModel.getTask());
        cv.put(COLUMN_STATUS, 0);
        db.insert(TABLE_NAME, null, cv);
    }

    public List<ToDoModel> getAllTasks() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if (cur != null) {

                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        int idIndex = cur.getColumnIndex(COLUMN_ID);
                        int taskIndex = cur.getColumnIndex(COLUMN_TASK);
                        int statusIndex = cur.getColumnIndex(COLUMN_STATUS);
                        if (idIndex != -1 && taskIndex != -1 && statusIndex != -1) {
                            task.setId(cur.getInt(idIndex));
                            task.setTask(cur.getString(taskIndex));
                            task.setStatus(cur.getInt(statusIndex));
                            taskList.add(task);
                        }
                    } while (cur.moveToNext());
                }
                while (cur.moveToNext()) ;
            }
        } finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK, task);
        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}

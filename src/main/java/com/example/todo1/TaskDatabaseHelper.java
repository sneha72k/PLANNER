package com.example.todo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
//a
public class TaskDatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 3; // Increment this if you change schema

    // Table and Column Names
    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "due_date";
    private static final String COLUMN_TIME = "due_time";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_COMPLETED = "completed"; // 0 = not completed, 1 = completed

    // Constructor
    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_COMPLETED + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_TABLE);
    }

    // Called when the database version is increased
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_COMPLETED + " INTEGER DEFAULT 0");
        }
    }

    // Add a new task to the database
    public void addTask(String taskName, String dueDate, String dueTime, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //key value map to enter data
        values.put(COLUMN_NAME, taskName);
        values.put(COLUMN_DATE, dueDate);
        values.put(COLUMN_TIME, dueTime);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_COMPLETED, 0); // Default: not completed
        db.insert(TABLE_NAME, null, values);

    }
//--------------------------------s
    // Get all tasks from the database
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
                //select arg-no arguement
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String dueDate = cursor.getString(2);
                String dueTime = cursor.getString(3);
                String category = cursor.getString(4);
                int completed = cursor.getInt(5);

                taskList.add(new Task(id, name, dueDate, dueTime, category));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return taskList;
    }

    // Delete task by ID
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            //placeholder-new string-places id
    }

    // Mark a task as completed by ID
    public void markTaskCompleted(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, 1);
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Get only incomplete tasks
    public List<Task> getIncompleteTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_COMPLETED + "=0", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String dueDate = cursor.getString(2);
                String dueTime = cursor.getString(3);
                String category = cursor.getString(4);
                int completed = cursor.getInt(5);

                taskList.add(new Task(id, name, dueDate, dueTime, category));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return taskList;
    }
}

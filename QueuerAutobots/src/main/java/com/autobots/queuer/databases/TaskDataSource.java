package com.autobots.queuer.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.autobots.queuer.models.Task;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by modsoussi on 1/21/14.
 */
public class TaskDataSource {
    private SQLiteDatabase database;
    private TaskOpenHelper dbHelper;
    private String[] allColumns = { TaskOpenHelper.COLUMN_ID,
            TaskOpenHelper.COLUMN_SERVER_ID,
            TaskOpenHelper.COLUMN_PROJECT_SERVER_ID,
            TaskOpenHelper.COLUMN_TEXT,
            TaskOpenHelper.COLUMN_COMPLETED,
            TaskOpenHelper.COLUMN_POSITION,
            TaskOpenHelper.COLUMN_CREATED,
            TaskOpenHelper.COLUMN_UPDATED,
            };

    public TaskDataSource(Context context) {
        dbHelper = new TaskOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task createTask(String text, int projectId, int serverId, int position, boolean completed) {
        ContentValues values = new ContentValues();
        values.put(TaskOpenHelper.COLUMN_SERVER_ID, serverId);
        values.put(TaskOpenHelper.COLUMN_PROJECT_SERVER_ID, projectId);
        values.put(TaskOpenHelper.COLUMN_POSITION, position);
        int complete = completed ? 1 : 0;
        values.put(TaskOpenHelper.COLUMN_COMPLETED, complete);
        values.put(TaskOpenHelper.COLUMN_TEXT, text);
        long insertId = database.insert(TaskOpenHelper.TABLE_TASKS, null,
                values);
        Cursor cursor = database.query(TaskOpenHelper.TABLE_TASKS,
                allColumns, TaskOpenHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void updateTask(Task task){
        ContentValues values = new ContentValues();
        values.put(TaskOpenHelper.COLUMN_SERVER_ID, task.getLocalId());
        values.put(TaskOpenHelper.COLUMN_PROJECT_SERVER_ID, task.getProjectId());
        values.put(TaskOpenHelper.COLUMN_POSITION, task.getPosition());
        int complete = task.isComplete() ? 1 : 0;
        values.put(TaskOpenHelper.COLUMN_COMPLETED, complete);
        values.put(TaskOpenHelper.COLUMN_TEXT, task.getName());

        database.update(TaskOpenHelper.TABLE_TASKS, values, TaskOpenHelper.COLUMN_SERVER_ID + " = ?",
                new String[] {String.valueOf(task.getLocalId())});
    }

    public void deleteTask(Task task) {
        String[] whereArgs = new String[1];
        whereArgs[0] = Integer.toString(task.getLocalId());
        database.delete(TaskOpenHelper.TABLE_TASKS, TaskOpenHelper.COLUMN_ID + " = ?",
                whereArgs);
    }

    /*
    ** TODO: implement getProjectTasks(int project_id)
     */
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(TaskOpenHelper.TABLE_TASKS,
                allColumns, null, null, null, null, null);

        if( cursor.moveToFirst()){
            tasks.add(cursorToTask(cursor));

            while(cursor.moveToNext()){
                tasks.add(cursorToTask(cursor));
            }
        }

        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(cursor.getColumnIndex(TaskOpenHelper.COLUMN_SERVER_ID)));
        task.setLocalId(cursor.getInt(cursor.getColumnIndex(TaskOpenHelper.COLUMN_ID)));
        task.setProjectId(cursor.getInt(cursor.getColumnIndex(TaskOpenHelper.COLUMN_PROJECT_SERVER_ID)));
        task.setName(cursor.getString(cursor.getColumnIndex(TaskOpenHelper.COLUMN_TEXT)));
        task.setPosition(cursor.getInt(cursor.getColumnIndex(TaskOpenHelper.COLUMN_POSITION)));
        task.setComplete(1 == cursor.getInt(cursor.getColumnIndex(TaskOpenHelper.COLUMN_COMPLETED)));
        return task;
    }
}


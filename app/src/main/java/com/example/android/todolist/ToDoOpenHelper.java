package com.example.android.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krit17 on 16-09-2017.
 */

public class ToDoOpenHelper extends SQLiteOpenHelper {
    static ToDoOpenHelper instance;

    public static ToDoOpenHelper getInstance(Context context) {
        if(instance==null){
            instance= new ToDoOpenHelper(context);
        }
        return instance;
    }

    private ToDoOpenHelper(Context context) {
        super(context,"TODO_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE "+Contract.TODO_TABLE+" ("+Contract.TODO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.TODO_TITLE+" TEXT, "+Contract.TODO_ACTIVITY+" TEXT, "+Contract.TODO_CAT+" TEXT, "+Contract.TODO_DATEANDTIME+" INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
}

package com.example.android.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

import static com.example.android.todolist.Constant.KEY_TODO;

public class CreateActivity extends AppCompatActivity  {
 EditText et1,et2,et3,et4,et5;
    public static final int REQ_SEND=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        et1=(EditText)findViewById(R.id.title);
        et2=(EditText)findViewById(R.id.activity);
        et3=(EditText) findViewById(R.id.category);
        et4=(EditText) findViewById(R.id.date);
        et5=(EditText) findViewById(R.id.time);

    }
    public void AddTodo(View view){
        String title=et1.getEditableText().toString();
        String activity=et2.getEditableText().toString();
        String category=et3.getEditableText().toString();
  //      String val=et4.getEditableText().toString();
//        int val_date=Integer.parseInt(val);

        ToDoOpenHelper openHelper=ToDoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db=openHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.TODO_TITLE,title);
        contentValues.put(Contract.TODO_ACTIVITY,activity);
        long id=db.insert(Contract.TODO_TABLE,null,contentValues);
       // ToDo todo=new ToDo(title,activity);
        Intent intent=new Intent();
        intent.putExtra(Constant.KEY_ID,id);
       // intent.putExtra(KEY_TODO,todo);
        setResult(REQ_SEND,intent);
        finish();

    }
}

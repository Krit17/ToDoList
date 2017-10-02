package com.example.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.android.todolist.Constant.KEY_POSITION;
import static com.example.android.todolist.Constant.KEY_TODO;

import static com.example.android.todolist.Constant.TEXT;

public class DetailedActivity extends AppCompatActivity {
    EditText editText;
    int pos;
    public final static int REQ_SUCCESS=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent i=getIntent();
        ToDo todo=(ToDo)i.getSerializableExtra(KEY_TODO);
        pos=i.getIntExtra(KEY_POSITION,0);
        editText=(EditText) findViewById(R.id.title);
        editText.setText(todo.getTitle());
    }
    public void Submit(View view){
       String text=editText.getEditableText().toString();
        Intent intent=new Intent();
        intent.putExtra(TEXT,text);
        intent.putExtra(KEY_POSITION,pos);
        setResult(REQ_SUCCESS,intent);
        finish();

    }
}

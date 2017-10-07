package com.example.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.android.todolist.Constant.KEY_POSITION;
import static com.example.android.todolist.Constant.KEY_TODO;

import static com.example.android.todolist.Constant.TEXT;

public class DetailedActivity extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;
    int pos;
    public final static int REQ_SUCCESS=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent i=getIntent();
        Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show();
        ToDo todo=(ToDo)i.getSerializableExtra(KEY_TODO);
        pos=i.getIntExtra(KEY_POSITION,0);

        et1=(EditText)findViewById(R.id.title1);
        et2=(EditText)findViewById(R.id.activity1);
        et3=(EditText) findViewById(R.id.category1);
        et4=(EditText) findViewById(R.id.date1);
        et5=(EditText) findViewById(R.id.time1);
        et1.setText(todo.getTitle());
    }
    public void Submit(View view){
       String text=et1.getEditableText().toString();
        Intent intent=new Intent();
        intent.putExtra(TEXT,text);
        intent.putExtra(KEY_POSITION,pos);
        setResult(REQ_SUCCESS,intent);
        finish();

    }
}

package com.example.android.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.todolist.Constant.KEY_ID;
import static com.example.android.todolist.Constant.KEY_POSITION;
import static com.example.android.todolist.Constant.KEY_TODO;
import static com.example.android.todolist.Constant.TEXT;


public class MainActivity extends AppCompatActivity  {
    ListView listView;
    ArrayList<ToDo> todolist;
    CustomAdapter adapter;
    View view;
    String value;
    SharedPreferences sharedPreferences;
    public static final int SAVE_DETAIL = 1;
    public static final int CREATE_DETAIL=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LifeCycle","Create");
        super.onCreate(savedInstanceState);
       // sharedPreferences=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //Gson gson=new Gson();
        //Set<ToDo> todolists=sharedPreferences.get
        //SharedPreferences.Editor editor=sharedPreferences.edit();
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        todolist = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            ToDo item = new ToDo("Expense" + i, "Activity" + i);
//
//            todolist.add(item);
//        }
        ToDoOpenHelper openHelper=ToDoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db=openHelper.getReadableDatabase();
        Cursor cursor=db.query(Contract.TODO_TABLE,null,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(Contract.TODO_ID));
            String title=cursor.getString(cursor.getColumnIndex(Contract.TODO_TITLE));
            String activity=cursor.getString(cursor.getColumnIndex(Contract.TODO_ACTIVITY));
            ToDo todo=new ToDo(title,activity,id);
            todolist.add(todo);

        }
        cursor.close();
        adapter = new CustomAdapter(this, todolist,  new CustomAdapter.CallBackAdapter() {
            @Override
            public void DeleteTodo(int position, View view) {


                    ToDo todo=todolist.get(position);
                    int id=todo.getId();
                    ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(getApplicationContext());
                    SQLiteDatabase db = openHelper.getWritableDatabase();
                    db.delete(Contract.TODO_TABLE, Contract.TODO_ID + " = ? ", new String[]{id + ""});
                    todolist.remove(position);
                    adapter.notifyDataSetChanged();

            }
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDo todo = todolist.get(i);
                Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
                intent.putExtra(KEY_TODO, todo);
                intent.putExtra(KEY_POSITION, i);
                startActivityForResult(intent, SAVE_DETAIL);

                //todo=(String) adapterView.getAdapter().getItem(i);
                //Toast.makeText(MainActivity.this,todo,Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent intent2=new Intent(this,CreateActivity.class);
            startActivityForResult(intent2,CREATE_DETAIL);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Add Todo Item");
//            view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
//
//            builder.setView(view);
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    EditText text=(EditText)view.findViewById(R.id.name);
//                    value=text.getEditableText().toString();
//
//                    todolist.add(value);
//                    adapter.notifyDataSetChanged();
                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();

//        else if (id == R.id.remove) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Delete todo");
//            builder.setMessage("Are you sure?");
//            builder.setCancelable(false);
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    todolist.remove(todolist.size() - 1);
//                    adapter.notifyDataSetChanged();
//                }
//            });
//            builder.setNegativeButton("No", null);
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
        else if(id==R.id.about){
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String url="http://codingninjas.in";
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        else if(id==R.id.feedback){
            Intent feedback=new Intent();
            feedback.setAction(Intent.ACTION_SENDTO);
            String mail="mailto:krithikaa1705@gmail.com";
            feedback.setData(Uri.parse(mail));
            startActivity(feedback);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SAVE_DETAIL && resultCode == DetailedActivity.REQ_SUCCESS) {

            String text = data.getStringExtra(TEXT);
            int pos = data.getIntExtra(KEY_POSITION, 0);
            ToDo todo = todolist.get(pos);
            todo.setTitle(text);
            adapter.notifyDataSetChanged();
        }
        if(requestCode==CREATE_DETAIL&&resultCode==CreateActivity.REQ_SEND){

            long id=data.getLongExtra(KEY_ID,-1L);
            if(id>-1){
                ToDoOpenHelper openHelper=ToDoOpenHelper.getInstance(getApplicationContext());
                SQLiteDatabase db=openHelper.getReadableDatabase();
                Cursor cursor=db.query(Contract.TODO_TABLE,null,
                        Contract.TODO_ID+" = ?",new String[]{id+" "},null,null,null);
            if(cursor.moveToFirst()){
                String title=cursor.getString(cursor.getColumnIndex(Contract.TODO_TITLE));
                String activity=cursor.getString(cursor.getColumnIndex(Contract.TODO_ACTIVITY));
                ToDo todo=new ToDo(title,activity,(int)id);
                todolist.add(todo);

            }
            cursor.close();
            }
           // ToDo todo=(ToDo)data.getSerializableExtra(KEY_TODO);



            adapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //@Override
//    public void DeleteTodo(View view,final int position) {
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("Delete ToDo");
//        builder.setMessage("Are you sure??");
//        builder.setNegativeButton("NO",null);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                ToDo todo=todolist.get(position);
//                int id=todo.getId();
//                ToDoOpenHelper openHelper=ToDoOpenHelper.getInstance(getApplicationContext());
//                SQLiteDatabase db=openHelper.getWritableDatabase();
//                db.delete(Contract.TODO_TABLE,Contract.TODO_ID+" = ? ", new String[]{id+""});
//                todolist.remove(position);
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        AlertDialog dialog=builder.create();
//        dialog.show();
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle Event","Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle","Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle","Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","Destroy");
    }
}



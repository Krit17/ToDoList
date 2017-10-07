package com.example.android.todolist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;
import static com.example.android.todolist.R.id.activity;

/**
 * Created by Krit17 on 09-09-2017.
 */

public class CustomAdapter extends ArrayAdapter<ToDo> {
    Context mcontext;
    ViewHolder viewHolder;
    ArrayList<ToDo> mitem;
     CallBackAdapter mcallback;
    public CustomAdapter(@NonNull Context context, ArrayList<ToDo> todolist, CallBackAdapter callBackAdapter) {
        super(context, 0);
        mcontext=context;
        mitem=todolist;
        mcallback=callBackAdapter;
    }

    @Override
    public int getCount() {

        return  mitem.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.detail_row_layout, null);
            viewHolder=new ViewHolder();
            viewHolder.title=(TextView)convertView.findViewById(R.id.Title);
            viewHolder.task=(TextView)convertView.findViewById(R.id.activity);
            Button b=(Button)convertView.findViewById(R.id.Submit);
            b.setFocusable(false);
            viewHolder.button=b;
            convertView.setTag(viewHolder);
        }


        viewHolder=(ViewHolder)convertView.getTag();
        viewHolder.button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.button.setFocusable(true);
                if(mcallback!=null){
                    mcallback.DeleteTodo(position,view);
                }
            }
        });

        ToDo item=mitem.get(position);
        Log.d("pos",position+"");
        viewHolder.title.setText(item.getTitle());
        viewHolder.task.setText(item.getTask());
        return convertView;
    }
    static class ViewHolder{
        TextView title;
        TextView task;
        Button button;
    }

    public interface CallBackAdapter {
        void DeleteTodo(int position,View view);
    }

}

package com.momtaz.todo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momtaz.todo.AddNewTask;
import com.momtaz.todo.MainActivity;
import com.momtaz.todo.Model.ToDoModel;
import com.momtaz.todo.R;
import com.momtaz.todo.Utils.Databasehelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> mList;
    private MainActivity activity;
    private Databasehelper myDB;
    public ToDoAdapter(Databasehelper myDB,MainActivity activity)
    {
        this.activity =activity;
        this.myDB=myDB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item=mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toboolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                 myDB.updatestatus(item.getId(),1);
                }else
                    myDB.updatestatus(item.getId(),0);
            }
        });

    }
    public boolean toboolean(int num)
    {
        return num!=0;
    }
    public Context getContext()
    {
        return activity;
    }

    public void settasks(List<ToDoModel>mList)
    {
        this.mList=mList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position)
    {
        ToDoModel item= mList.get(position);
        myDB.deletetask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);

    }
    public void editItem(int position)
    {


        ToDoModel item =mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());
        AddNewTask task =new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox= itemView.findViewById(R.id.mcheckbox);
        }
    }
}

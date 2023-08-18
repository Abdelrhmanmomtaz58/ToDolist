package com.momtaz.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momtaz.todo.Adapter.ToDoAdapter;
import com.momtaz.todo.Model.ToDoModel;
import com.momtaz.todo.Utils.Databasehelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onDialogCloseListner{
    private Databasehelper myDB;
    private List<ToDoModel>mList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab = findViewById(R.id.fab);
        myDB=new Databasehelper(MainActivity.this);
        mList =new ArrayList<>();
        adapter = new ToDoAdapter(myDB,MainActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mList=myDB.getAllTask();
        Collections.reverse(mList);
        adapter.settasks(mList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);

            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList=myDB.getAllTask();
        Collections.reverse(mList);
        adapter.settasks(mList);
        adapter.notifyDataSetChanged();
    }
}
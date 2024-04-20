package com.hav.firstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hav.firstapp.Adapter.ToDoAdapter;
import com.hav.firstapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.hav.firstapp.Utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(db, this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });


        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        taskAdapter.setTasks(taskList);
    }

    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId() == R.id.search){
            Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();

            return true;
        }
        else if(item.getItemId() == R.id.about){
            Intent intent = new Intent(this, ContactActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(ContactActivity.KEY_SHOW_WHAT, ContactActivity.VALUE_SHOW_ABOUT);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId() == R.id.help){
            Intent intent = new Intent(this, ContactActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(ContactActivity.KEY_SHOW_WHAT, ContactActivity.VALUE_SHOW_HELP);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
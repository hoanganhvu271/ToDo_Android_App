package com.hav.firstapp;

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
import com.hav.firstapp.Adapter.ToDoAdapter;
import com.hav.firstapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private List<ToDoModel> taskList;

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
        
        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(this);
        taskRecyclerView.setAdapter(taskAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("This is a test task");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        taskAdapter.setTasks(taskList);
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
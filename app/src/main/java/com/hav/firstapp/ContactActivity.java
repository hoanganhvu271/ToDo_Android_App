package com.hav.firstapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactActivity extends AppCompatActivity {

    public static final String KEY_SHOW_WHAT = "show_what";
    public static final String VALUE_SHOW_ABOUT = "show_about";
    public static final String VALUE_SHOW_HELP = "show_help";

    //DataSource
    private static final String[] items = {"item1", "item2", "item3",
            "item4", "item5", "item6",
            "item7", "item8", "item9",
            "item10", "item11", "item12",
            "item13", "item14", "item15",
            "item16", "item17", "item18",
            "item19", "item20", "item21",
            "item22", "item23", "item24"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //Content Provider
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle.getString(KEY_SHOW_WHAT).equals(VALUE_SHOW_ABOUT)){
            //ListAdapter
            ListView myListView = findViewById(R.id.my_listview);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            myListView.setAdapter(adapter);
            myListView.setOnItemClickListener( (parent, view, position, id) -> {
                Toast.makeText(this, "Item clicked: " + items[position], Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
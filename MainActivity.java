package com.example.arjuncp.quicknotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

   static ArrayList<String> quicknotes = new ArrayList<>();
   static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.arjuncp.quicknotes", Context.MODE_PRIVATE);

        ListView listView = findViewById(R.id.listView);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("quicknotes",null);
        if(set==null){
            quicknotes.add("Example Note");
        } else{
            quicknotes = new ArrayList(set);
        }


        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, quicknotes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), editorActivity.class);
                intent.putExtra("Id",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemtodel = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you Sure ?")
                        .setMessage("Do you want to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                quicknotes.remove(itemtodel);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set= new HashSet<>(MainActivity.quicknotes);
                                sharedPreferences.edit().putStringSet("quicknotes", set).apply();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.extrafunctionality, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_new_note){

            Intent intent = new Intent(getApplicationContext(), editorActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}

package com.example.arjuncp.quicknotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class editorActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        id = intent.getIntExtra("Id",-1);

        if(id != -1){
            editText.setText(MainActivity.quicknotes.get(id));

        } else {
            MainActivity.quicknotes.add("");
            id = MainActivity.quicknotes.size() -1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.quicknotes.set(id,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.arjuncp.quicknotes", Context.MODE_PRIVATE);
                HashSet<String> set= new HashSet<>(MainActivity.quicknotes);
                sharedPreferences.edit().putStringSet("quicknotes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

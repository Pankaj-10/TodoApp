package com.gesh.punkaj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.gesh.punkaj.R;

public class TodoActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        title = findViewById(R.id.editText_todoTitle);
        description = findViewById(R.id.editText_todoDescription);
        numberPicker = findViewById(R.id.numberPicker_todoPriority);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit Todo");
            title.setText(intent.getStringExtra("title"));
            description.setText(intent.getStringExtra("description"));
            numberPicker.setValue(intent.getIntExtra("priority", 1));
        } else {
            setTitle("Add Todo");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_todo) {
            saveTodo();
        }
        return true;
    }

    private void saveTodo() {
        String todoTitle = title.getText().toString();
        String todoDescription = description.getText().toString();
        int todoPriority = numberPicker.getValue();

        Intent intent = new Intent();
        intent.putExtra("todoTitle", todoTitle);
        intent.putExtra("todoDescription", todoDescription);
        intent.putExtra("todoPriority", todoPriority);
        int id = getIntent().getIntExtra("id",-1);
        if (id != -1){
            intent.putExtra("id",id);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

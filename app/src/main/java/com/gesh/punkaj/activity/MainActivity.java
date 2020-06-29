package com.gesh.punkaj.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gesh.punkaj.R;
import com.gesh.punkaj.adapter.TodoAdapter;
import com.gesh.punkaj.fragment.DeleteAllDialogFragment;
import com.gesh.punkaj.model.Todo;
import com.gesh.punkaj.viewmodel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TodoAdapter.onItemClickListner, DeleteAllDialogFragment.DialogListener {

    TodoViewModel todoViewModel;
    RecyclerView recyclerView;
    TodoAdapter todoAdapter;
    FloatingActionButton floatingActionButton;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingButton_todoAdd);
        recyclerView = findViewById(R.id.recyclerView_todoDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        todoAdapter = new TodoAdapter();
        recyclerView.setAdapter(todoAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                todoViewModel.delete(todoAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllNotes().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                // update Recycler View
                todoAdapter.setTodos(todos);
            }
        });

        floatingActionButton.setOnClickListener(this);
        todoAdapter.setOnItemClickListner(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_delete_all,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all_todo){
            showDialog();
        } if(id==R.id.implicit){
            Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ielts.britishcouncil.org/nepal"));
            startActivity(implicit);
        }
        return true;
    }

    void showDialog() {
        DialogFragment newFragment = new DeleteAllDialogFragment();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onFinishEditDialog(Boolean isDeleted) {
        if(isDeleted) {
            System.out.println("isdeleted"+isDeleted);
            todoViewModel.deleteAll();
            Toast.makeText(this, "List Deleted Successfully!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "List Not Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatingButton_todoAdd:
                Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String note_title = data.getStringExtra("todoTitle");
            String note_description = data.getStringExtra("todoDescription");
            int note_priority = data.getIntExtra("todoPriority",0);

            todoViewModel.insert(new Todo(note_title,note_description,note_priority));

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            String note_title = data.getStringExtra("todoTitle");
            String note_description = data.getStringExtra("todoDescription");
            int note_priority = data.getIntExtra("todoPriority",0);
            int note_id = data.getIntExtra("id",0);

            Todo todo = new Todo(note_title,note_description,note_priority);
            todo.setId(note_id);
            todoViewModel.update(todo);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Todo todo) {
        Intent intent = new Intent(MainActivity.this, TodoActivity.class);
        intent.putExtra("id", todo.getId());
        intent.putExtra("title", todo.getTitle());
        intent.putExtra("description", todo.getDescription());
        intent.putExtra("priority", todo.getPriority());
        startActivityForResult(intent,EDIT_NOTE_REQUEST);

    }
}

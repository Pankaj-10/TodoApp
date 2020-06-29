package com.gesh.punkaj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gesh.punkaj.model.Todo;
import com.gesh.punkaj.repo.TodoRepository;

import java.util.List;


public class TodoViewModel extends AndroidViewModel {

    private TodoRepository todoRepository;
    private LiveData<List<Todo>> allNotes;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        allNotes = todoRepository.getAllNotes();
    }

    public void insert(Todo todo) {
        todoRepository.insert(todo);
    }

    public void update(Todo todo) {
        todoRepository.update(todo);
    }

    public void delete(Todo todo) {
        todoRepository.delete(todo);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }

    public LiveData<List<Todo>> getAllNotes() {
        return allNotes;
    }

}


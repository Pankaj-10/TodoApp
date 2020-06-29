package com.gesh.punkaj.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gesh.punkaj.dao.TodoDao;
import com.gesh.punkaj.db.TodoDatabase;
import com.gesh.punkaj.model.Todo;

import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> allNotes;

    public TodoRepository(Application application){
        TodoDatabase todoDatabase = TodoDatabase.getInstance(application);
        todoDao = todoDatabase.noteDao();
        allNotes = todoDao.getAllNotes();
    }

    public void insert(Todo todo){
        new InsertAsyncTask(todoDao).execute(todo);
    }

    public void update(Todo todo){
        new UpdateAsyncTask(todoDao).execute(todo);
    }

    public void delete(Todo todo){
        new DeleteAsyncTask(todoDao).execute(todo);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(todoDao).execute();
    }

    public LiveData<List<Todo>> getAllNotes(){
        return allNotes;
    }

    private class InsertAsyncTask extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        InsertAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.insert(todos[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        UpdateAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.update(todos[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        DeleteAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.delete(todos[0]);
            return null;
        }
    }


    private class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void> {

        private TodoDao todoDao;

        DeleteAllAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAll();
            return null;
        }
    }
}

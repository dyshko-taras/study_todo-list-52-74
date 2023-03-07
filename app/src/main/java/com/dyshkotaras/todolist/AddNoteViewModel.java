package com.dyshkotaras.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AddNoteViewModel extends AndroidViewModel {
    public AddNoteViewModel(@NonNull Application application) {
        super(application);
//        noteDatabase = NoteDatabase.getInstance(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

//    private NoteDatabase noteDatabase;
    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();


    public void saveNote(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.add(note);
                shouldCloseScreen.postValue(true);
            }
        });
        thread.start();
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }
}

package com.dyshkotaras.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        notesDao.add(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true);
                    }
                });

    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }
}

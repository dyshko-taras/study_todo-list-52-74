package com.dyshkotaras.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNote;
    private final Database database = Database.getInstance();
    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                database.remove(note.getId());
                showNotes();
            }
        });
        recyclerViewNotes.setAdapter(notesAdapter);
        buttonAddNote.setOnClickListener(view -> {
            Intent intent = AddNoteActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initView() {
        buttonAddNote = findViewById(R.id.buttonAddNote);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

    }

    private void showNotes() {
        notesAdapter.setNotes(database.getNotes());
    }
}


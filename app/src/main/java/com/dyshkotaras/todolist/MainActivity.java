package com.dyshkotaras.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNote;
    private LinearLayout linearLayoutNotes;
    private Database database = Database.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
        linearLayoutNotes = findViewById(R.id.linerLayoutNotes);
    }

    private void showNotes() {
        linearLayoutNotes.removeAllViews();
        for (Note note : database.getNotes()) {
            View view = getLayoutInflater().inflate(R.layout.note_item,
                    linearLayoutNotes,
                    false);
            view.setOnClickListener(view1 -> {
                database.remove(note.getId());
                showNotes();
            });
            TextView textViewNote = view.findViewById(R.id.textViewNote);
            textViewNote.setText(note.getText());
            int colorResId;
            switch (note.getPriority()) {
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorResId = android.R.color.holo_red_light;
                    break;

            }
            int color = ContextCompat.getColor(this, colorResId);
            textViewNote.setBackgroundColor(color);
            linearLayoutNotes.addView(view);
        }
    }

}
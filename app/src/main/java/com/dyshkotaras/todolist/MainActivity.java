package com.dyshkotaras.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNote;
    private LinearLayout linearLayoutNotes;
    private ArrayList<Note> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note" + i, random.nextInt(3));
            notes.add(note);

        }
        showNotes();
    }

    private void initView() {
        buttonAddNote = findViewById(R.id.buttonAddNote);
        linearLayoutNotes = findViewById(R.id.linerLayoutNotes);
    }

    private void showNotes() {
        for (Note note : notes) {
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    linearLayoutNotes,
                    false);
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
            Log.d("int colorResId ---- ",String.valueOf(colorResId));
            int color = ContextCompat.getColor(this,colorResId);
            textViewNote.setBackgroundColor(color);
            Log.d("int color ---- ",String.valueOf(color));
            linearLayoutNotes.addView(view);
        }
    }

}
package com.example.shake_to_undo_notes_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_NOTE = 1;
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Note> notes;
    MaterialButton mb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mb= findViewById(R.id.addNote);

        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addNoteActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        notes = new ArrayList<>();
        adapter = new MyAdapter(this, notes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addNote).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, addNoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            String title = data.getStringExtra("note_title");
            String desc = data.getStringExtra("note_desc");
            long time = data.getLongExtra("note_time", System.currentTimeMillis());

            Note newNote = new Note(title, desc, time);
            adapter.addNote(newNote);
        }
    }
}
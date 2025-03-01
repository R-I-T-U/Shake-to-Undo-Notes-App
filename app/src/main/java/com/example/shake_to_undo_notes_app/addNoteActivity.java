package com.example.shake_to_undo_notes_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class addNoteActivity extends AppCompatActivity {
    private boolean isEditMode = false;
    private int notePosition = -1;
    private EditText t, d;
    private MaterialButton mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
         t = findViewById(R.id.input_title);
         d = findViewById(R.id.input_desc);
         mb = findViewById(R.id.save);

        if (getIntent().hasExtra("note_title")) {
            isEditMode = true;
            t.setText(getIntent().getStringExtra("note_title"));
            d.setText(getIntent().getStringExtra("note_desc"));
            notePosition = getIntent().getIntExtra("note_position", -1);
        }
        
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = t.getText().toString();
                String desc = d.getText().toString();
                long created_time = System.currentTimeMillis();

                if(title.isEmpty() || desc.isEmpty()){
                    Toast.makeText(addNoteActivity.this, "Please fill up all the fields", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent();
                i.putExtra("note_title",title);
                i.putExtra("note_desc",desc);
                i.putExtra("note_time", created_time);

                if (isEditMode) {
                    // Pass the position of the note being edited
                    i.putExtra("note_position", notePosition);
                }

                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
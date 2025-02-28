package com.example.shake_to_undo_notes_app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class addNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EditText t = findViewById(R.id.input_title);
        EditText d = findViewById(R.id.input_desc);
        MaterialButton mb = findViewById(R.id.save);



        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = t.getText().toString();
                String desc = d.getText().toString();
                long created_time = System.currentTimeMillis();
            }
        });
    }
}
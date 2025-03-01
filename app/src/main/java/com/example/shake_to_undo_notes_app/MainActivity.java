package com.example.shake_to_undo_notes_app;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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
    public static final int REQUEST_CODE_EDIT_NOTE = 2;
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Note> notes;
    MaterialButton mb;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initialize views
        mb = findViewById(R.id.addNote);
        recyclerView = findViewById(R.id.recyclerview);

        // Initialize notes list and adapter
        notes = new ArrayList<>();
        adapter = new MyAdapter(this, notes);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Set up the "Add Note" button
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });

        // Initialize the shake detector
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();

        // Set the shake listener
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                // Restore the deleted note when the phone is shaken
                adapter.restoreDeletedNote();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the shake detector when the activity resumes
        if (accelerometer != null) {
            sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        // Unregister the shake detector when the activity is paused
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK) {
            String title = data.getStringExtra("note_title");
            String desc = data.getStringExtra("note_desc");
            long time = data.getLongExtra("note_time", System.currentTimeMillis());

            if(requestCode == REQUEST_CODE_ADD_NOTE){
                Note newNote = new Note(title, desc, time);
                adapter.addNote(newNote);
            } else if (requestCode == REQUEST_CODE_EDIT_NOTE) {
                int position = data.getIntExtra("note_position", -1);
                if(position != -1){
                    Note updatedNote = new Note(title, desc, time);
                    adapter.updateNote(position, updatedNote);
                }
            }

        }
    }
}
package com.example.noteskeeper;

import com.example.noteskeeperapp.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etContent = findViewById(R.id.etContent);
        Button btnSave = findViewById(R.id.btnSave);

        DatabaseHelper db = new DatabaseHelper(this);

        int noteId = getIntent().getIntExtra("id", -1);

        if (noteId != -1) {
            etTitle.setText(getIntent().getStringExtra("title"));
            etContent.setText(getIntent().getStringExtra("content"));
        }

        btnSave.setOnClickListener(v -> {
            if (noteId == -1) {
                db.insertNote(
                        etTitle.getText().toString(),
                        etContent.getText().toString()
                );
            } else {
                db.updateNote(
                        noteId,
                        etTitle.getText().toString(),
                        etContent.getText().toString()
                );
            }
            finish();
        });

    }
}

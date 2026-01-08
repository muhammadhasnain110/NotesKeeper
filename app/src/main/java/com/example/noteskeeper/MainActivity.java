package com.example.noteskeeper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.noteskeeperapp.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        Button btnAdd = findViewById(R.id.btnAdd);

        db = new DatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NoteAdapter(this, db.getAllNotes()));


        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddNoteActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new NoteAdapter(this, db.getAllNotes()));

    }
}

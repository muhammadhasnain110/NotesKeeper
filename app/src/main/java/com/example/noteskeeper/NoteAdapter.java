package com.example.noteskeeper;

import com.example.noteskeeperapp.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    ArrayList<Note> list;
    Context context;
    DatabaseHelper db;

    public NoteAdapter(Context context, ArrayList<Note> list) {
        this.context = context;
        this.list = list;
        db = new DatabaseHelper(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            content = view.findViewById(R.id.tvContent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = list.get(position);

        holder.title.setText(note.title);
        holder.content.setText(note.content);

        // LONG PRESS FOR MENU
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu menu = new PopupMenu(context, v);
            menu.inflate(R.menu.note_menu);

            menu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent intent = new Intent(context, AddNoteActivity.class);
                    intent.putExtra("id", note.id);
                    intent.putExtra("title", note.title);
                    intent.putExtra("content", note.content);
                    context.startActivity(intent);
                }

                if (item.getItemId() == R.id.menu_delete) {
                    new AlertDialog.Builder(context)
                            .setTitle("Delete Note")
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes", (d, w) -> {
                                db.deleteNote(note.id);
                                list.remove(position);
                                notifyDataSetChanged();
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                return true;
            });
            menu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

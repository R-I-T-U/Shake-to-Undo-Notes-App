package com.example.shake_to_undo_notes_app;

import static com.example.shake_to_undo_notes_app.MainActivity.REQUEST_CODE_EDIT_NOTE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Note> notes;
    private Note recentlyDeletedNote;
    private int recentlyDeletedNotePosition;
    public MyAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_notes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.desc.setText(note.getDesc());
        holder.time.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(note.getTime()));

        //delete on long click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            // Store the deleted note and its position
                            recentlyDeletedNote = notes.get(position);
                            recentlyDeletedNotePosition = position;

                            // Remove the note from the list
                           notes.remove(position);
                           notifyItemRemoved(position);
                           notifyItemRangeChanged(position, notes.size()-position);
                            Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });

        // edit on click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = notes.get(position);
                Intent i = new Intent(context, addNoteActivity.class);
                i.putExtra("note_title", note.getTitle());
                i.putExtra("note_desc", note.getDesc());
                i.putExtra("note_time", note.getTime());
                i.putExtra("note_position", position);
                ((Activity) context).startActivityForResult(i, REQUEST_CODE_EDIT_NOTE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public void addNote(Note note) {
        notes.add(0, note);
        notifyItemInserted(0);
    }
    public void restoreDeletedNote() {
        if (recentlyDeletedNote != null) {
            // Add the note back to its original position
            notes.add(recentlyDeletedNotePosition, recentlyDeletedNote);
            notifyItemInserted(recentlyDeletedNotePosition);
            recentlyDeletedNote = null; // Clear the stored note
            Toast.makeText(context, "Note restored", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateNote(int position, Note updatedNote){
        notes.set(position, updatedNote);
        notifyItemChanged(position);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.output_title);
            desc = itemView.findViewById(R.id.output_desc);
            time = itemView.findViewById(R.id.output_time);
        }
    }
}

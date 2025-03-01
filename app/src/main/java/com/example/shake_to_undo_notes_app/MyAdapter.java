package com.example.shake_to_undo_notes_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Note> notes;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.desc.setText(note.getDesc());
        holder.time.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(note.getTime()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addNote(Note note) {
        notes.add(note);
        notifyItemInserted(notes.size() - 1);
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

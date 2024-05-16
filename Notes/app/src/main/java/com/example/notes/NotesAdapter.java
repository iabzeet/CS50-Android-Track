package com.example.notes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerVew;
        TextView textView;

        NoteViewHolder(View view) {
            super(view);
            containerVew = view.findViewById(R.id.note_row);
            textView = view.findViewById(R.id.note_row_text);

            containerVew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note current = (Note) containerVew.getTag();
                    Intent intent = new Intent(v.getContext(), NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        //use this position param to get a note
        Note current = notes.get(position);
        //set the text of this text view to be the text of the note
        holder.textView.setText(current.contents);
        holder.containerVew.setTag(current);
    }

    //method o geet the numbeer of notes inside of the adapter

    @Override
    public int getItemCount() {
        return notes.size();
    }

    //load everything from our db
    public void reload() {
        notes = MainActivity.dataBase.noteDao().getAllNotes();
        notifyDataSetChanged();
    }
}


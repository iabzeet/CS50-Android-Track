package com.example.notes;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//when this activity is created we want to set the text of this edittext
//to be the note thats in the database, so when we open this up for the first time
//the user just sees what was there before

//when the user leaves this activity we want we want to save the current contents
//of that edittext back to the db

public class NoteActivity extends AppCompatActivity {
    private EditText editText;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = findViewById(R.id.note_edit_text);

        //lets set the contents to be the contents of the note the user selected
        //loading the note
        String contents = getIntent().getStringExtra("contents");
        id = getIntent().getIntExtra("id", 0);
        editText.setText(contents);
    }

    //save the note
    //called when we leave the activity
    //before this acivity is closed, when the user hit back button
    //lets persist everything that we have to disk
    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.dataBase.noteDao().save(editText.getText().toString(), id);
    }
}
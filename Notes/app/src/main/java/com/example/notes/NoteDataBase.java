package com.example.notes;
//this class will use note as it was annotated with @Entity
//we'll use more annotations provided by room library
//collects all the dao together

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}

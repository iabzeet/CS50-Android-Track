package com.example.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//we'll use room library here
//adding annotations providede to us fromm room library

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "contents")
    public String contents;
}

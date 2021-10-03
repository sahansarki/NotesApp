package com.sahan.notesapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sahan.notesapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase(){

    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}

/**
 * use_cases they just want to get the data that's all use cases care about

 * use_cases contain our business logic

 * the advantage of use cases are that on the one hand they make our code very readable
   because a use case is in the end just a single thing pur app does a single user action
 */
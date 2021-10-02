package com.sahan.notesapp.feature_note.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sahan.notesapp.feature_note.domain.model.Note
import dagger.Provides
import kotlinx.coroutines.flow.Flow


interface NoteRepository {
    /*
    Why interface?
    that's just good for testing because then we can easily
    create fake versions of this repository
     */

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    /**
     * These definitions are not only for repositories also for other classes

     * If you have these definitions in form of an interface these belong in the domain layer.

     * Implementations of that so where we actually say okay these nodes come from a database

     * This insertion(insertNote()) should happen in the database that happens in the data layer.
     */
}
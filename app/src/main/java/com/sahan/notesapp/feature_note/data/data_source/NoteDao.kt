package com.sahan.notesapp.feature_note.data.data_source

import androidx.room.*
import com.sahan.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>
    /*
    
    By using Flow to handle streams of values, you can transform data in complex multi-threaded ways,
    by writing just a small bit of code!

    You probably know that suspending functions can return a single value asynchronously.
    When using suspending functions,
    you don’t have to worry about threading, the Coroutines API does that for you!
    Flow, however, can return multiple values asynchronously, and over time.

    Why this one isnt suspend?
    Because this one returns a flow
     */

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    /*
        onConflict ,
        REPLACE , that means if we call this insert function with an id
        that already exists in our database then it will just update the existing entry

     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}
package com.sahan.notesapp.feature_note.data.repository

import com.sahan.notesapp.feature_note.data.data_source.NoteDao
import com.sahan.notesapp.feature_note.domain.model.Note
import com.sahan.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository{
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
        /**
         * We just return dao.getNotes() so because that's a very simple app we only
         have a database there wont be much logic in this repository. We just call the functions
         from our dao.

         * But as soon as you also have an api in here then things get more complex and
         you actually have some data logic in here
         */
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}
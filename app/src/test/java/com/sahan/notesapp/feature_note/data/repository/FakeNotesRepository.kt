package com.sahan.notesapp.feature_note.data.repository

import com.sahan.notesapp.feature_note.domain.model.Note
import com.sahan.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNotesRepository: NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes)}
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find{ it.id == id}
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

}
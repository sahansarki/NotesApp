package com.sahan.notesapp.feature_note.presentation.notes

import com.sahan.notesapp.feature_note.domain.model.Note
import com.sahan.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()


}

package com.sahan.notesapp.feature_note.presentation.util

// that will just contain our different screens and the corresponding routes
sealed class Screen(val route: String) {
    object NotesScreen: Screen("notes_screen")
    object AddEditNoteScreen: Screen("add_edit_note_screen")
}

package com.sahan.notesapp.di

import android.app.Application
import androidx.room.Room
import com.sahan.notesapp.feature_note.data.data_source.NoteDatabase
import com.sahan.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.sahan.notesapp.feature_note.domain.repository.NoteRepository
import com.sahan.notesapp.feature_note.domain.use_case.AddNote
import com.sahan.notesapp.feature_note.domain.use_case.DeleteNote
import com.sahan.notesapp.feature_note.domain.use_case.GetNotes
import com.sahan.notesapp.feature_note.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(repository: NoteRepository): NotesUseCases{
        return NotesUseCases(
            getNotes = GetNotes(repository = repository),
            deleteNote = DeleteNote(repository = repository),
            addNote = AddNote(repository = repository)

        )
    }

}
package com.sahan.notesapp.di

import android.app.Application
import androidx.room.Room
import com.sahan.notesapp.feature_note.data.data_source.NoteDatabase
import com.sahan.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.sahan.notesapp.feature_note.domain.repository.NoteRepository
import com.sahan.notesapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// This is a container for dependecies that live a specific amount of time.
// we define all dependencies that just live as long as our application does

/**
 * That could for example be a retrofit instance , a room instance so just objects that we need throughout the whole lifetime of our application
But with dagger-hilt we also have the option to scope our dependencies so we cannot only have an app module with singleton dependencies.

 * We can also have for example a main activity module so a module that contains dependencies that only live as long as our main activity does.
And when we then switch the activity we cant inject those main activity dependencies anymore in another activity.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * return olarak ;
       what do we provide? well NoteDatabase for example.
       in this function we return whatever we want to provide and whatever we want to inject in our classes later
     */
    @Provides // tell dagger-hilt this function we want to provide a dependency which is this NoteDatabase in our case
    @Singleton // if we wouldnt have this annotation then every time we inject the NoteDatabase it would create a new instance of it
    fun provideNoteDatabase(app: Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotes = GetNotes(repository = repository),
            deleteNote = DeleteNote(repository = repository),
            addNote = AddNote(repository = repository),
            getNote = GetNote(repository)

        )
    }

}
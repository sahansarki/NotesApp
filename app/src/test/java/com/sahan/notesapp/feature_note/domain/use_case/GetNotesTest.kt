package com.sahan.notesapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.sahan.notesapp.feature_note.data.repository.FakeNotesRepository
import com.sahan.notesapp.feature_note.domain.model.Note
import com.sahan.notesapp.feature_note.domain.util.NoteOrder
import com.sahan.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNotesRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNotesRepository()
        getNotes = GetNotes(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach{ fakeRepository.insertNote(it)}
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for(i in 0..notes.size -2){ // -2 -> because now we will take a look at two notes that are next to each other and just check that the first note is actually less than second one
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }

    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for(i in 0..notes.size -2){ // -2 -> because now we will take a look at two notes that are next to each other and just check that the first note is actually less than second one
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }

    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for(i in 0..notes.size -2){ // -2 -> because now we will take a look at two notes that are next to each other and just check that the first note is actually less than second one
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }

    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for(i in 0..notes.size -2){ // -2 -> because now we will take a look at two notes that are next to each other and just check that the first note is actually less than second one
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }

    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for(i in 0..notes.size -2){ // -2 -> because now we will take a look at two notes that are next to each other and just check that the first note is actually less than second one
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }

    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for(i in 0..notes.size -2){ // -2 -> because now we will take a look at two notes that are next to each other and just check that the first note is actually less than second one
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }

    }
}
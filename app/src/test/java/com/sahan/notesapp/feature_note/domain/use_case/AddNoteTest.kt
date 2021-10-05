package com.sahan.notesapp.feature_note.domain.use_case


import com.sahan.notesapp.feature_note.data.repository.FakeNotesRepository
import com.sahan.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: FakeNotesRepository
    private var notesToInsert = mutableListOf<Note>()

    @Before
    fun setUp() {
        fakeRepository = FakeNotesRepository()
        addNote = AddNote(fakeRepository)


        ('a'..'z').forEachIndexed { index, c ->
            if (index % 2 == 0) notesToInsert.add(
                Note(
                    title = "",
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
            else if (index % 3 == 0) {
                notesToInsert.add(
                    Note(
                        title = c.toString(),
                        content = "",
                        timestamp = index.toLong(),
                        color = index
                    )
                )
            } else if(index % 5 == 0){
                notesToInsert.add(
                    Note(
                        title = "",
                        content = "",
                        timestamp = index.toLong(),
                        color = index
                    )
                )
            }

            else notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()


    }

    @Test
    fun `Save notes without title`() = runBlocking {
        val notesWithoutTitle = mutableListOf<Note>()
        notesToInsert.forEach {
            if(it.title == "") notesWithoutTitle.add(it)
        }

        notesWithoutTitle.forEach{
            addNote(it)
        }
    }

    @Test
    fun `Save notes without content`() = runBlocking {
        val notesWithoutContent = mutableListOf<Note>()
        notesToInsert.forEach {
            if(it.content == "") notesWithoutContent.add(it)
        }

        notesWithoutContent.forEach{
            addNote(it)
        }
    }

    @Test
    fun `Save notes without title and content`() = runBlocking {
        val notesWithoutTitleContent = mutableListOf<Note>()
        notesToInsert.forEach {
            if(it.title == "" && it.content == "") notesWithoutTitleContent.add(it)
        }
        notesWithoutTitleContent.forEach{
            addNote(it)
        }
    }

}
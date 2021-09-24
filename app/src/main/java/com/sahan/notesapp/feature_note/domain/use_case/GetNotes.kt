package com.sahan.notesapp.feature_note.domain.use_case

import com.sahan.notesapp.feature_note.domain.model.Note
import com.sahan.notesapp.feature_note.domain.repository.NoteRepository
import com.sahan.notesapp.feature_note.domain.util.NoteOrder
import com.sahan.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * That's our use case that's business logic and it contains how it actually accesses our repository.

 * now this(class GetNotes) can be resused in a whole app so if you maybe multiple screens in which
   you need to access a list of nodes with a given order then you can just reuse this use case class so
   you dont need to put this logic below in all of your view models.
 */


class GetNotes(
    private val repository: NoteRepository
    // Make sure that you actually make this of the interface type and note of the implementation
    // type because otherwise its not replaceable that's the whole purpose of using an interface
) {

    operator fun invoke(
        noteOrder: NoteOrder// = NoteOrder.Date(OrderType.Descending)
    ) : Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            /*
            we want to map the result list so whatever list we get from our repository
            we map this to a new list
             */
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}
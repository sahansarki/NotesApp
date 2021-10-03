package com.sahan.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahan.notesapp.feature_note.domain.model.Note
import com.sahan.notesapp.feature_note.domain.use_case.NotesUseCases
import com.sahan.notesapp.feature_note.domain.util.NoteOrder
import com.sahan.notesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * inject annotation,
   we can annotate this with a inject and this will actually assign the value of this variable behind the scenes for us so
   when we launch our app we know the value of this viewmodel and that is exactly the one in AppModule.

 * So what will actually happen behind the scenes is dagger hilt will see okay we want a variable here or an object and that object is of type NoteUseCases.
   then it will just search in all of its modules. If it can find a NotesUseCases dependency
   then it says here is a dependency that is provided which is of type NotesUseCases so i will simply take this NotesUseCases and assign it to this variable.
 */
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NotesUseCases
    // It's the wrapper class so we can access all the use cases we have for our notes feature
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())


    // This will be the state that contains the values our ui will observe
    // A value holder where reads to the value property during the execution of a Composable function,
    // the current RecomposeScope will be subscribed to changes of that value.
    val state: State<NotesState> = _state

    private var recentlyDeledNotes: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeledNotes = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeledNotes ?: return@launch)
                    recentlyDeledNotes = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }


    /**
     * That will now return a flow that comes from our database or room will rather
     trigger this flow and emit something new whenever something changes in our
     database but we kind of want to map this to our compose state so we can say .onEach
     for each emittion
     */
    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}
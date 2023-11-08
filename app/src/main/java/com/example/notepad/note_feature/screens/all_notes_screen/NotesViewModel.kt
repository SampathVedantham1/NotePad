package com.example.notepad.note_feature.screens.all_notes_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.use_case.NoteUseCases
import com.example.notepad.note_feature.domain.util.NoteOrderByFilter
import com.example.notepad.note_feature.domain.util.OrderTypeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A view model class that handles the state of the notes screen.
 */
@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {
    // The state of the note
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var fetchNotesJob: Job? = null

    init {
        fetchNotes(noteOrderByFilter = NoteOrderByFilter.Title(orderType = OrderTypeFilter.Ascending))
    }

    /**
     *  A function that handles the events from the UI and updates the state accordingly
     *
     *  @param event The event that occurred on the UI
     */
    fun onEvent(event: NoteEvents) {
        when (event) {
            is NoteEvents.Order -> {
                // used class as if not referential equality are compared which will never be same
                if (state.value.noteOrderByFilter::class == event.noteOrder::class && state.value.noteOrderByFilter.orderTypeFilter == event.noteOrder.orderTypeFilter) {
                    return
                }
                fetchNotes(event.noteOrder)
            }

            is NoteEvents.Delete -> {
                viewModelScope.launch {
                    recentlyDeletedNote = event.note
                    noteUseCases.deleteNote(event.note)
                }
            }

            is NoteEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addOrEditNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NoteEvents.ChangeFilterOptions -> {
                _state.value = state.value.copy(
                    isFilterOptionsVisible = !state.value.isFilterOptionsVisible
                )
            }
        }
    }

    /**
     *  A function that fetches the notes from the use cases and updates the state with the result
     *
     *  @param noteOrderByFilter The filter for ordering the notes by title, date, or color
     */
    private fun fetchNotes(noteOrderByFilter: NoteOrderByFilter) {
        Log.d("mybug", "fetchNotes: chech for fetch notes")
        fetchNotesJob?.cancel()
        fetchNotesJob =
            noteUseCases.fetchAllNotes(noteOrderByFilter = noteOrderByFilter).onEach { notes ->
                _state.value =
                    state.value.copy(notesList = notes, noteOrderByFilter = noteOrderByFilter)
            }.launchIn(viewModelScope)
    }
}

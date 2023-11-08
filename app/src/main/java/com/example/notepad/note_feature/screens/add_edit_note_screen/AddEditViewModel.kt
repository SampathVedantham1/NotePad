package com.example.notepad.note_feature.screens.add_edit_note_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.note_feature.domain.model.InvalidNoteException
import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A ViewModel class that handles the logic and state of the add/edit note screen.
 *
 * @param noteUseCases The use cases for performing operations on notes.
 * @param savedStateHandle The handle for saving and restoring the state of the screen.
 */
@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // The state of the note title input field
    private val _noteTitle = mutableStateOf(NoteTextState(hint = "Type Title of the note..."))
    val noteTitle: State<NoteTextState> = _noteTitle

    // The state of the note content input field
    private val _noteContent = mutableStateOf(NoteTextState(hint = "Content..."))
    val noteContent: State<NoteTextState> = _noteContent

    // The state of the note color selection.
    private val _noteColor = mutableStateOf<Int>(Note.backgroundColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    // The flow of UI events that need to be handled by the screen
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // The id of the current note being edited, or null if adding a new note
    private var currentNoteId: Int? = null

    init {
        // Check if there is a note id passed from the previous screen
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                // If there is a valid note id, get the note from the use cases and update the state
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title, isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content, isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    /**
     * Handles the events from the UI and updates the state accordingly.
     *
     * @param event The event that occurred on the UI.
     */
    fun onEvent(event: AddEditEvent) {
        when (event) {
            // When the user enters the title, update the title state
            is AddEditEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(text = event.title)
            }

            // When the user changes the focus of the title, update the hint visibility
            is AddEditEvent.TitleFocusState -> {
                _noteTitle.value =
                    noteTitle.value.copy(isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank())
            }

            // When the user enters the content, update the content state
            is AddEditEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(text = event.content)
            }

            // When the user changes the focus of the content, update the hint visibility
            is AddEditEvent.ContentFocusState -> {
                _noteContent.value =
                    noteContent.value.copy(isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank())
            }

            // When the user changes the color, update the color state
            is AddEditEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            // When the user saves the note, try to add or edit the note using the use cases
            is AddEditEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addOrEditNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        // If successful, emit a save note event to the UI
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        // If failed, emit a show snackbar event to the UI with the error message
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Note was not saved."
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     * A sealed class that represents the possible UI events that can occur on the add/edit note screen
     * */
    sealed class UiEvent {
        // A data class that represents the event of showing a snackbar with a message
        data class ShowSnackbar(val message: String) : UiEvent()

        // An object that represents the event of saving a note
        object SaveNote : UiEvent()
    }

}
package com.example.notepad.note_feature.screens.all_notes_screen

import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.util.NoteOrderByFilter
import com.example.notepad.note_feature.domain.util.OrderTypeFilter

/**
 * A data class that represents the state of the Notes list.
 */
data class NotesState(
    val notesList: List<Note> = emptyList(),
    val noteOrderByFilter: NoteOrderByFilter = NoteOrderByFilter.Title(OrderTypeFilter.Ascending),
    val isFilterOptionsVisible: Boolean = false,
)

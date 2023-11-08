package com.example.notepad.note_feature.screens.all_notes_screen

import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.util.NoteOrderByFilter

sealed class NoteEvents {
    data class Order(val noteOrder: NoteOrderByFilter) : NoteEvents()
    data class Delete(val note: Note) : NoteEvents()
    object RestoreNote : NoteEvents()
    object ChangeFilterOptions : NoteEvents()
}

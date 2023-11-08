package com.example.notepad.note_feature.screens.add_edit_note_screen

/**
 *  A data class that represents the state of a text input field
 */
data class NoteTextState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
)

package com.example.notepad.note_feature.screens.add_edit_note_screen

import androidx.compose.ui.focus.FocusState

sealed class AddEditEvent {
    /**
     * Event for when the title text is entered or changed
     */
    data class EnteredTitle(val title: String) : AddEditEvent()

    /**
     * Event for when the content text is entered or changed
     */
    data class EnteredContent(val content: String) : AddEditEvent()

    /**
     * Event for when the focus state of the title input field changes
     */
    data class TitleFocusState(val focusState: FocusState) : AddEditEvent()

    /**
     * Event for when the focus state of the content input field changes
     */
    data class ContentFocusState(val focusState: FocusState) : AddEditEvent()

    /**
     * Event for changing the note's background color
     */
    data class ChangeColor(val color: Int) : AddEditEvent()

    /**
     * Event for saving the note
     */
    object SaveNote : AddEditEvent()
}



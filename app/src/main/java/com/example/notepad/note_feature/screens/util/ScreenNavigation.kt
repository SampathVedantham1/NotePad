package com.example.notepad.note_feature.screens.util

sealed class ScreenNavigation(val route: String) {
    object NoteScreen : ScreenNavigation("notes_screen")
    object AddEditScreen : ScreenNavigation("add_edit_screen")
}

package com.example.notepad.note_feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notepad.ui.theme.BluePastel
import com.example.notepad.ui.theme.BrownPastel
import com.example.notepad.ui.theme.GreenPastel
import com.example.notepad.ui.theme.LavendarPastel
import com.example.notepad.ui.theme.PinkPastel
import com.example.notepad.ui.theme.RedPastel
import com.example.notepad.ui.theme.YellowPastel

/**
 * Represents a Note entity stored in the database.
 *
 * @property title The title of the note.
 * @property content The content or description of the note.
 * @property timestamp The timestamp when the note was created or last modified.
 * @property color The color associated with the note for visual distinction.
 * @property id The unique identifier for the note (primary key).
 */
@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        // List of predefined background colors for notes.
        val backgroundColors = listOf(
            RedPastel,
            BrownPastel,
            YellowPastel,
            GreenPastel,
            BluePastel,
            LavendarPastel,
            PinkPastel
        )
    }
}

/**
 * Custom exception class for handling invalid notes.
 *
 * @param message The error message describing the reason for the exception.
 */
class InvalidNoteException(message: String): Exception(message)

package com.example.notepad.note_feature.domain.use_case

/**
 * Data class to group and manage different use cases related to Note entities.
 *
 * @property fetchAllNotes The use case for fetching and ordering all notes.
 * @property deleteNote The use case for deleting a Note.
 * @property addOrEditNote The use case for adding or editing a Note.
 * @property getNote The use case for fetching a Note by its ID.
 */
data class NoteUseCases(
    val fetchAllNotes: FetchAllNotes,
    val deleteNote: DeleteNote,
    val addOrEditNote: AddOrEditNote,
    val getNote: FetchNoteById
)

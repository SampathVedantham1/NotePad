package com.example.notepad.note_feature.domain.use_case

import com.example.notepad.note_feature.domain.model.InvalidNoteException
import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.repository.NoteRepository

/**
 * Use case for adding or editing a Note in the repository.
 *
 * @param repository The repository for managing Note entities.
 */
class AddOrEditNote(private val repository: NoteRepository) {

    /**
     * Adds or edits a Note in the repository after performing validation checks.
     *
     * @param note The Note object to be added or updated.
     * @throws InvalidNoteException if the title or content of the note is blank.
     */
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can not be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Title can not be empty.")
        }
        repository.addOrEditNote(note)
    }
}
package com.example.notepad.note_feature.domain.use_case

import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.repository.NoteRepository

/**
 * Use case for deleting a Note from the repository.
 *
 * @param noteRepository The repository for managing Note entities.
 */
class DeleteNote(private val noteRepository: NoteRepository) {

    /**
     * Deletes a specified Note from the repository.
     *
     * @param selectedNote The Note to be deleted.
     */
    suspend operator fun invoke(selectedNote: Note) {
        noteRepository.deleteNote(note = selectedNote)
    }
}
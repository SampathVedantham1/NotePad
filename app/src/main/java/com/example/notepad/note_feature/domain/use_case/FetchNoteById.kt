package com.example.notepad.note_feature.domain.use_case

import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.repository.NoteRepository

/**
 * Use case for fetching a Note by its ID from the repository.
 *
 * @param repository The repository for managing Note entities.
 */
class FetchNoteById(private val repository: NoteRepository) {

    /**
     * Retrieves a specific Note from the repository by its ID.
     *
     * @param id The ID of the note to fetch.
     * @return The Note object if found, or null if not found.
     */
    suspend operator fun invoke(id: Int): Note? {
        return repository.fetchNoteById(id)
    }
}
package com.example.notepad.note_feature.domain.repository

import com.example.notepad.note_feature.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing Note entities.
 */
interface NoteRepository {

    /**
     * Retrieves all notes from the repository and provides them as a Flow of List<Note>.
     *
     * @return A Flow emitting a list of notes.
     */
    fun getAllNotes(): Flow<List<Note>>

    /**
     * Retrieves a specific note from the repository by its ID.
     *
     * @param id The ID of the note to fetch.
     * @return The Note object if found, or null if not found.
     */
    suspend fun fetchNoteById(id: Int): Note?

    /**
     * Adds a new note to the repository or updates an existing note with the same ID.
     *
     * @param note The Note object to be added or updated.
     */
    suspend fun addOrEditNote(note: Note)

    /**
     * Deletes a note from the repository.
     *
     * @param note The Note object to be deleted.
     */
    suspend fun deleteNote(note: Note)
}
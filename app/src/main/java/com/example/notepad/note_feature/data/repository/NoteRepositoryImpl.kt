package com.example.notepad.note_feature.data.repository

import com.example.notepad.note_feature.data.data_source.NoteDataAccessObject
import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the NoteRepository interface, responsible for interacting with the data source.
 *
 * @param dao The NoteDataAccessObject used to perform database operations.
 */
class NoteRepositoryImpl(private val dao: NoteDataAccessObject):NoteRepository{
    /**
     * Retrieves all notes from the data source and provides them as a Flow of List<Note>.
     *
     * @return A Flow emitting a list of notes.
     */
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }

    /**
     * Retrieves a specific note from the data source by its ID.
     *
     * @param id The ID of the note to fetch.
     * @return The Note object if found, or null if not found.
     */
    override suspend fun fetchNoteById(id: Int): Note? {
        return dao.fetchNoteById(id = id)
    }

    /**
     * Inserts a new note into the data source or updates an existing note with the same ID.
     *
     * @param note The Note object to be added or updated.
     */
    override suspend fun addOrEditNote(note: Note) {
        dao.addOrEditNote(note = note)
    }

    /**
     * Deletes a note from the data source.
     *
     * @param note The Note object to be deleted.
     */
    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note = note)
    }
}
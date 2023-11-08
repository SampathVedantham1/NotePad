package com.example.notepad.note_feature.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notepad.note_feature.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for performing database operations related to Note entities.
 */
@Dao
interface NoteDataAccessObject {

    /**
     * Retrieves all notes from the database and provides them as a Flow of List<Note>.
     *
     * @return A Flow emitting a list of notes.
     */
    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    /**
     * Retrieves a specific note from the database by its ID.
     *
     * @param id The ID of the note to fetch.
     * @return The Note object if found, or null if not found.
     */
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun fetchNoteById(id: Int): Note?

    /**
     * Inserts a new note into the database or updates an existing note with the same ID.
     *
     * @param note The Note object to be added or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrEditNote(note: Note)

    /**
     * Deletes a note from the database.
     *
     * @param note The Note object to be deleted.
     */
    @Delete
    suspend fun deleteNote(note: Note)
}
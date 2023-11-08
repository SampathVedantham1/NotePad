package com.example.notepad.note_feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notepad.note_feature.domain.model.Note

/**
 * Room Database class for managing and accessing the Note entities.
 *
 * @property entities List of entity classes associated with this database.
 * @property version The version of the database.
 */
@Database(entities = [Note::class], version = 2)
abstract class NoteDataBase: RoomDatabase() {
    /**
     * Provides access to data access operations for the Note entity.
     */
    abstract val noteDataAccessObject: NoteDataAccessObject

    /**
     * A companion object to store constant values related to the database.
     */
    companion object {
        // Name of the Database
        const val DATABASE_NAME = "note_database"
    }
}
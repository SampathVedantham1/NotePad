package com.example.notepad.note_feature.domain.use_case

import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.domain.repository.NoteRepository
import com.example.notepad.note_feature.domain.util.NoteOrderByFilter
import com.example.notepad.note_feature.domain.util.OrderTypeFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for fetching and ordering all notes from the repository based on various filters.
 *
 * @param repository The repository for managing Note entities.
 */
class FetchAllNotes(private val repository: NoteRepository) {

    /**
     * Retrieves a Flow of all notes from the repository and orders them based on the specified filters.
     *
     * @param noteOrderByFilter The filter criteria for ordering notes (default: Title in ascending order).
     * @return A Flow emitting a list of ordered notes.
     */
    operator fun invoke(
        noteOrderByFilter: NoteOrderByFilter = NoteOrderByFilter.Title(
            OrderTypeFilter.Ascending
        ),
    ): Flow<List<Note>> {
        return repository.getAllNotes().map { listOfNotes ->
            if (noteOrderByFilter.orderTypeFilter == OrderTypeFilter.Ascending) {
                when (noteOrderByFilter) {
                    is NoteOrderByFilter.Title -> listOfNotes.sortedBy { note -> note.title.lowercase() }

                    is NoteOrderByFilter.Date -> listOfNotes.sortedBy { note -> note.title.lowercase() }

                    is NoteOrderByFilter.Color -> listOfNotes.sortedBy { note -> note.title.lowercase() }
                }
            } else {
                when (noteOrderByFilter) {
                    is NoteOrderByFilter.Title -> listOfNotes.sortedByDescending { note -> note.title.lowercase() }

                    is NoteOrderByFilter.Date -> listOfNotes.sortedByDescending { note -> note.title.lowercase() }

                    is NoteOrderByFilter.Color -> listOfNotes.sortedByDescending { note -> note.title.lowercase() }
                }
            }
        }
    }
}
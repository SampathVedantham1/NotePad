package com.example.notepad.DependencyInjection

import android.app.Application
import androidx.room.Room
import com.example.notepad.note_feature.data.data_source.NoteDataBase
import com.example.notepad.note_feature.data.repository.NoteRepositoryImpl
import com.example.notepad.note_feature.domain.repository.NoteRepository
import com.example.notepad.note_feature.domain.use_case.AddOrEditNote
import com.example.notepad.note_feature.domain.use_case.DeleteNote
import com.example.notepad.note_feature.domain.use_case.FetchAllNotes
import com.example.notepad.note_feature.domain.use_case.FetchNoteById
import com.example.notepad.note_feature.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dagger Hilt Module for providing application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the Room database instance for notes.
     *
     * @param application The application context.
     * @return The NoteDataBase instance.
     */
    @Provides
    @Singleton
    fun providesNoteDatabase(application: Application): NoteDataBase {
        return Room.databaseBuilder(
            application,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provides the implementation of the NoteRepository.
     *
     * @param dataBase The NoteDataBase instance.
     * @return The NoteRepository implementation.
     */
    @Provides
    @Singleton
    fun providesNoteRepository(dataBase: NoteDataBase): NoteRepository {
        return NoteRepositoryImpl(dataBase.noteDataAccessObject)
    }

    /**
     * Provides the NoteUseCases, which encapsulates various use cases related to notes.
     *
     * @param repository The NoteRepository implementation.
     * @return The NoteUseCases instance.
     */
    @Provides
    @Singleton
    fun providesNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            fetchAllNotes = FetchAllNotes(repository),
            deleteNote = DeleteNote(repository),
            addOrEditNote = AddOrEditNote(repository),
            getNote = FetchNoteById(repository)
        )
    }
}
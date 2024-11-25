package com.example.roomdatabase.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.roomdatabase.data.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun geNotesStream(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<Note>

    @Upsert
    suspend fun upsertNote(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)

}
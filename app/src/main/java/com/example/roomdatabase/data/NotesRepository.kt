package com.example.roomdatabase.data

import com.example.roomdatabase.data.database.NotesDao

class NotesRepository(
    private val notesDao: NotesDao
) {
    fun getNotesStream() = notesDao.geNotesStream()

    fun getNoteById(id: Int) = notesDao.getNoteById(id)

    suspend fun upsertNote(note: Note) = notesDao.upsertNote(note)

    suspend fun deleteNote(id: Int) = notesDao.deleteNote(id)

}
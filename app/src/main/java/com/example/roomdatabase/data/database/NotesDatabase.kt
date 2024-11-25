package com.example.roomdatabase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.data.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var instance: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }

}
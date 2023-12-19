package ru.protei.galimullindg.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.protei.galimullindg.domain.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao():NotesDao
}
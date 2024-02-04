package ru.protei.galimullindg.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.galimullindg.domain.Note
import ru.protei.galimullindg.domain.NotesRepository
import javax.inject.Inject

class NotesRepositoryDB @Inject constructor(private val notesDao: NotesDao) : NotesRepository {
    override fun all(): List<Note> {
        return notesDao.all()
    }

    override fun allFlow(): Flow<List<Note>> {
        return notesDao.allFlow()
    }

    override suspend fun insert(note: Note): Long {
        var id: Long
        withContext(Dispatchers.IO) {
            id = notesDao.insert(note)
        }
        return id
    }

    override suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.update(note)
        }
    }

    override suspend fun deleteById(id: Long) {
        withContext(Dispatchers.IO) {
            notesDao.deleteById(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            notesDao.deleteAll()
        }
    }

    override suspend fun byRemoteId(remoteId: Long): Note? {
        var note: Note?
        withContext(Dispatchers.IO) {
            note = notesDao.byRemoteId(remoteId)
        }
        return note
    }
}
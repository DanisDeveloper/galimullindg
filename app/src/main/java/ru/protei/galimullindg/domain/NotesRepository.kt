package ru.protei.galimullindg.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun all(): List<Note>
    fun allFlow(): Flow<List<Note>>
    suspend fun insert(note: Note): Long
    suspend fun update(note: Note)
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun byRemoteId(remoteId:Long): Note?
}
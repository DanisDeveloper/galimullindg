package ru.protei.galimullindg.domain

interface NotesRemoteRepository {
    suspend fun list():List<Note>
    suspend fun add(note: Note): Long?
    suspend fun update(note: Note):Boolean
    suspend fun delete(note: Note): Boolean
}
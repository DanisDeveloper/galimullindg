package ru.protei.galimullindg.domain

import kotlinx.coroutines.flow.Flow


class NotesUseCase(private val notesRepo: NotesRepository) {

    fun notesFlow(): Flow<List<Note>> {
        return notesRepo.allFlow()
    }

    suspend fun save(note: Note) {
        if (note.id == null)
            this.add(note)
        else
            this.update(note)
    }

    private suspend fun add(note: Note): Long {
        return notesRepo.insert(note)
    }

    private suspend fun update(note: Note) {
        notesRepo.update(note)
    }

    suspend fun fillWithInitialNotes(initialNotes: List<Note> = emptyList()) {
        notesRepo.deleteAll()
        initialNotes.forEach {
            notesRepo.insert(it)
        }
    }
}
package ru.protei.galimullindg.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesUseCase @Inject constructor(
    private val notesRepo: NotesRepository,
    private val notesApi: NotesRemoteRepository
) {

    fun notesFlow(): Flow<List<Note>> {
        return notesRepo.allFlow()
    }

    suspend fun save(note: Note) {
        if (note.id == null) {
            this.add(note.apply {
                this.remoteId = this@NotesUseCase.remoteAdd(note)
            })
        } else {
            this.remoteUpdate(note)
            this.update(note)
        }
    }

    private suspend fun add(note: Note): Long {
        return notesRepo.insert(note)
    }

    private suspend fun update(note: Note) {
        notesRepo.update(note)
    }

    private suspend fun remoteAdd(note: Note): Long? {
        return notesApi.add(note)
    }

    private suspend fun remoteUpdate(note: Note) {
        notesApi.update(note)
    }

    suspend fun loadRemoteNotes() {
        val list = notesApi.list()
        list.forEach { item ->
            val note = notesRepo.byRemoteId(item.remoteId!!)
            if (note == null) {
                this.add(item)
            } else {
                this.update(note.apply{
                    this.title = item.title
                    this.text = item.text
                })
            }
        }
    }
}
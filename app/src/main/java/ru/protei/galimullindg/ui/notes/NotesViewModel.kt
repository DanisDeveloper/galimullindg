package ru.protei.galimullindg.ui.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.protei.galimullindg.domain.Note

class NotesViewModel : ViewModel() {

    var selected = mutableStateOf<Note?>(null)
        private set

    val notes = mutableStateListOf<Note>(
        Note("Андройд", "операционная система, которая открыта для всех"),
        Note("Why I Love Kotlin", "Lambdas and inline functions"),
        Note("I Love Coding", "programming is a hobby")
    )

    fun onNoteChange(title: String, text: String) {
        selected.value?.title = title
        selected.value?.text = text
    }

    fun onEditComplete() {
        selected.value = null
    }

    fun onNoteSelected(note: Note) {
        selected.value = note
    }

    fun onAddNoteClicked() {
        val note = Note("", "")
        notes.add(note)
        selected.value = note
    }

}
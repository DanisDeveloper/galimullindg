package ru.protei.galimullindg.ui.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.protei.galimullindg.domain.Note
import ru.protei.galimullindg.domain.NotesUseCase

class NotesViewModel(val notesUseCase: NotesUseCase) : ViewModel() {

    var selected = mutableStateOf<Note?>(null)
        private set

    val notes = MutableStateFlow<List<Note>>(emptyList())

    init {
        viewModelScope.launch {
//            notesUseCase.fillWithInitialNotes(
//                mutableStateListOf(
//                    Note(
//                        title = "Андройд",
//                        text = "операционная система, которая открыта для всех"
//                    ),
//                    Note(title = "Why I Love Kotlin", text = "Lambdas and inline functions"),
//                    Note(title = "I Love Coding", text = "programming is a hobby")
//                )
//            )

            notesUseCase.notesFlow().collect {
                notes.value = it
            }
        }
    }

    fun onNoteChange(title: String, text: String) {
        selected.value?.title = title
        selected.value?.text = text
    }

    fun onEditComplete() {
        viewModelScope.launch {
            notesUseCase.save(selected.value!!)
        }
        selected.value = null
    }

    fun onNoteSelected(note: Note) {
        selected.value = note
    }

    fun onAddNoteClicked() {
        val note = Note(title = "", text = "")
        selected.value = note
    }
}
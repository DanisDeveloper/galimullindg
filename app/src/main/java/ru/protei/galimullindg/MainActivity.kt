package ru.protei.galimullindg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import ru.protei.galimullindg.data.NotesDatabase
import ru.protei.galimullindg.data.NotesRepositoryDB
import ru.protei.galimullindg.domain.NotesUseCase
import ru.protei.galimullindg.ui.notes.NotesScreen
import ru.protei.galimullindg.ui.notes.NotesViewModel
import ru.protei.galimullindg.ui.theme.GalimullindgTheme

class MainActivity : ComponentActivity() {

    private val database: NotesDatabase by lazy{
        Room.databaseBuilder(
            this,
            NotesDatabase::class.java,
            "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    private val notesRepo by lazy{
        NotesRepositoryDB(database.notesDao())
    }
    private val notesUseCase by lazy{
        NotesUseCase(notesRepo)
    }

    private val notesViewModel: NotesViewModel by viewModels(){
        viewModelFactory {
            initializer {
                NotesViewModel(notesUseCase)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalimullindgTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotesScreen(notesViewModel)
                }
            }
        }
    }
}

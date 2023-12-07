package ru.protei.galimullindg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.protei.galimullindg.ui.notes.NotesScreen
import ru.protei.galimullindg.ui.notes.NotesViewModel
import ru.protei.galimullindg.ui.theme.GalimullindgTheme

class MainActivity : ComponentActivity() {

    private val notesViewModel: NotesViewModel by viewModels()
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

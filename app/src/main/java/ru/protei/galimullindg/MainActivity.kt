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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.galimullindg.data.local.NotesDatabase
import ru.protei.galimullindg.data.local.NotesRepositoryDB
import ru.protei.galimullindg.data.remote.NotesGitHubApi
import ru.protei.galimullindg.data.remote.NotesGitHubRepository
import ru.protei.galimullindg.domain.NotesUseCase
import ru.protei.galimullindg.ui.notes.NotesScreen
import ru.protei.galimullindg.ui.notes.NotesViewModel
import ru.protei.galimullindg.ui.theme.GalimullindgTheme

class MainActivity : ComponentActivity() {

    private val database: NotesDatabase by lazy {
        Room.databaseBuilder(
            this,
            NotesDatabase::class.java,
            "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    var httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer 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"
                ).build()
            chain.proceed(request)
        }.build()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/DanisDeveloper/galimullindg/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val notesRepo by lazy {
        NotesRepositoryDB(database.notesDao())
    }
    private val notesApi by lazy { NotesGitHubRepository(retrofit.create(NotesGitHubApi::class.java)) }
    private val notesUseCase by lazy {
        NotesUseCase(notesRepo,notesApi)
    }

    private val notesViewModel: NotesViewModel by viewModels() {
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

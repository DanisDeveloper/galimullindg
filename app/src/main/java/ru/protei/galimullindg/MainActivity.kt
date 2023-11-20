package ru.protei.galimullindg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.protei.galimullindg.domain.Note
import ru.protei.galimullindg.ui.theme.GalimullindgTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var notes = listOf(Note("Андройд","операционная система, которая открыта для всех"),
            Note("Why I Love Kotlin","Lambdas and inline functions"),
            Note("I Love Coding","programming is a hobby"))
        setContent {
            GalimullindgTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column{
                        notes.forEach {
                            ShowNote(title = it.title, text = it.text)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowNote(title: String,text:String) {
    Column (
        modifier = Modifier.padding(20.dp)
    ){
        Text(
            text = title,
            fontSize = 22.sp
        )
        Text(
            text = text
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GalimullindgTheme {
        ShowNote(title = "Android", text = "Android is super")
    }
}
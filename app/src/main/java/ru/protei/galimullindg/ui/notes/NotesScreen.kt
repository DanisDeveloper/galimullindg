package ru.protei.galimullindg.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(vm: NotesViewModel) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (vm.selected.value == null) {
                    vm.onAddNoteClicked()
                } else {
                    vm.onEditComplete()
                }
            }) {
                if (vm.selected.value == null) {
                    Icon(Icons.Filled.Add, null)
                } else {
                    Icon(Icons.Filled.Check, null)
                }
            }
        }
    ) {
        if (vm.selected.value == null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                itemsIndexed(vm.notes) { index, note ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { vm.onNoteSelected(note) },
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(6.dp, 6.dp, 6.dp)
                        )
                        Text(
                            text = note.text,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(6.dp)
                        )

                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(it)
            ) {
                var title by remember { mutableStateOf(vm.selected.value!!.title) }
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                        vm.onNoteChange(it, vm.selected.value!!.text)
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = {
                        Text(
                            text = "Заголовок"
                        )
                    }
                )

                var text by remember { mutableStateOf(vm.selected.value!!.text) }
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        vm.onNoteChange(vm.selected.value!!.title, it)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    cursorBrush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary
                        )
                    ),
                    decorationBox = { innerTetxField ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            innerTetxField()
                        }
                    }
                )
            }
        }
    }
}
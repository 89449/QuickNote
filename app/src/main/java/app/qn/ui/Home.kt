package app.qn.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.clickable
import app.qn.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
	viewModel: NoteViewModel,
    onTitleClick: (Int) -> Unit,
    onFabClick: () -> Unit
) {
	
	val notes = viewModel.allNotes.collectAsState(initial = emptyList()).value
	
    Scaffold(
        topBar = {
            TopAppBar(
                title = {}
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onFabClick
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(contentPadding = it) {
            items(notes, key = { it.id }) { note ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onTitleClick(note.id) }
                ) {
                    Text(
                        text = note.title.ifEmpty { "(No Title)" },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
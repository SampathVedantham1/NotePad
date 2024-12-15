package com.example.notepad.note_feature.screens.all_notes_screen.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notepad.R
import com.example.notepad.note_feature.screens.all_notes_screen.NoteEvents
import com.example.notepad.note_feature.screens.all_notes_screen.NotesViewModel
import com.example.notepad.note_feature.screens.util.ScreenNavigation
import kotlinx.coroutines.launch

/**
 * A composable function that displays the title, filter options and all notes.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(navController: NavController, viewModel: NotesViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(ScreenNavigation.AddEditScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new note")
            }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Row with Title and Filter Icon button.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Notes", style = MaterialTheme.typography.h4)

                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteEvents.ChangeFilterOptions)
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter_list),
                        contentDescription = "Sort"
                    )
                }
            }

            // Adding animation for visibility for filter options.
            AnimatedVisibility(
                visible = state.isFilterOptionsVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    noteOrderByFilter = state.noteOrderByFilter,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvents.Order(it))
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Note item.
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notesList) { note ->
                    NoteItem(note = note, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(ScreenNavigation.AddEditScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                        }, onDeleteClick = {
                        viewModel.onEvent(NoteEvents.Delete(note))
                        coroutineScope.launch {
                            val action = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note is Deleted",
                                actionLabel = "Undo"
                            )
                            if (action == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(NoteEvents.RestoreNote)
                            }
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


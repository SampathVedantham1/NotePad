package com.example.notepad.note_feature.screens.add_edit_note_screen.composable

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notepad.note_feature.domain.model.Note
import com.example.notepad.note_feature.screens.add_edit_note_screen.AddEditEvent
import com.example.notepad.note_feature.screens.add_edit_note_screen.AddEditViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNote(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditViewModel = hiltViewModel(),
) {
    // Retrieve note title and content from the view model
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    // Create a scaffold state for displaying snackbar messages.
    val scaffoldState = rememberScaffoldState()

    // Create an animation for the note background color.
    val noteBAckgroundAnimation = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    // Create a coroutine scope
    val scope = rememberCoroutineScope()

    // Listen for events from the view model using a LaunchedEffect.
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditViewModel.UiEvent.ShowSnackbar -> {
                    // Show a snackbar message.
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditViewModel.UiEvent.SaveNote -> {
                    // Navigate up when the note is saved.
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            // Trigger the save note event when the floating action button is clicked.
            viewModel.onEvent(AddEditEvent.SaveNote)
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "Save the note")
        }
    }, scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(noteBAckgroundAnimation.value)
                .padding(16.dp)
        ) {
            // Row for selecting note background color.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Iterate through available note background colors.
                Note.backgroundColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color = color)
                            .border(
                                width = 3.dp, color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    // Animate the background color change.
                                    noteBAckgroundAnimation.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                // Trigger the change color event.
                                viewModel.onEvent(AddEditEvent.ChangeColor(color = colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Input field for the note title.
            HintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChanged = {
                    viewModel.onEvent(AddEditEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.TitleFocusState(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input field for the note content.
            HintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChanged = {
                    // Trigger the event when the content changes.
                    viewModel.onEvent(AddEditEvent.EnteredContent(it))
                },
                onFocusChange = {
                    // Trigger the event when the content field focus changes.
                    viewModel.onEvent(AddEditEvent.ContentFocusState(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
package com.example.notepad.note_feature.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notepad.note_feature.screens.add_edit_note_screen.composable.AddEditNote
import com.example.notepad.note_feature.screens.all_notes_screen.composable.NoteScreen
import com.example.notepad.note_feature.screens.util.ScreenNavigation
import com.example.notepad.ui.theme.NotePadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenNavigation.NoteScreen.route
                    ) {
                        composable(route = ScreenNavigation.NoteScreen.route) {
                            NoteScreen(navController = navController)
                        }
                        composable(
                            route = ScreenNavigation.AddEditScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNote(navController = navController, noteColor = color)
                        }
                    }
                }
            }
        }
    }
}
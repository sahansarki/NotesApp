package com.sahan.notesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sahan.notesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.sahan.notesapp.feature_note.presentation.notes.NoteScreen
import com.sahan.notesapp.feature_note.presentation.util.Screen

import com.sahan.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // so we can inject our view models with dagger hilt in this activity
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ){
                    
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NoteScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                                 /*
                                 we actually also want to add something to that route because
                                 we want to be able to pass arguments and both arguments
                                 are actually optional here so the note id and node color.

                                 so we are going to do this just as with get parameters
                                 in the url.

                                 so that way these parameters are also just optional so we
                                 dont need to pass them if we just click on add note then
                                 we dont have any of these
                                  */
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
                                 },

                             )

                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}


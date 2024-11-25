package com.example.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.roomdatabase.data.NotesRepository
import com.example.roomdatabase.data.database.NotesDao
import com.example.roomdatabase.data.database.NotesDatabase
import com.example.roomdatabase.ui.detail.DetailRoute
import com.example.roomdatabase.ui.detail.DetailScreen
import com.example.roomdatabase.ui.detail.DetailViewModel
import com.example.roomdatabase.ui.home.HomeRoute
import com.example.roomdatabase.ui.home.HomeScreen
import com.example.roomdatabase.ui.home.HomeViewModel
import com.example.roomdatabase.ui.new_note.NewNoteRoute
import com.example.roomdatabase.ui.new_note.NewNoteScreen
import com.example.roomdatabase.ui.new_note.NewNoteViewModel
import com.example.roomdatabase.ui.theme.RoomDatabaseTheme

class MainActivity : ComponentActivity() {
    private lateinit var notesRepository: NotesRepository
    private lateinit var notesDao: NotesDao
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var newNoteViewModel: NewNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesDao = NotesDatabase.getInstance(this).notesDao()
        notesRepository = NotesRepository(notesDao)

        enableEdgeToEdge()
        setContent {
            RoomDatabaseTheme {
                val navController = rememberNavController()
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = HomeRoute
                ) {
                    composable<HomeRoute> {
                        homeViewModel = HomeViewModel(notesRepository)
                        HomeScreen(
                            viewmodel = homeViewModel,
                            onNoteClick = { navController.navigate(DetailRoute(it)) },
                            onNewNote = { navController.navigate(NewNoteRoute) }
                        )
                    }

                    composable<DetailRoute> {
                        val detailRoute = it.toRoute<DetailRoute>()
                        detailViewModel = DetailViewModel(notesRepository, detailRoute.noteId)
                        DetailScreen(
                            viewModel = detailViewModel,
                            onNavigateBack = { navController.navigateUp() }

                        )
                    }

                    newNoteViewModel = NewNoteViewModel(notesRepository)
                    composable<NewNoteRoute> {
                        NewNoteScreen(
                            viewModel = newNoteViewModel,
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}


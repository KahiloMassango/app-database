package com.example.roomdatabase.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.data.Note
import com.example.roomdatabase.data.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    private val notesRepository: NotesRepository,
    noteId: Int,
): ViewModel() {

    var uiState: StateFlow<Note> = notesRepository.getNoteById(noteId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            Note(0, "", "", "")
        )


}
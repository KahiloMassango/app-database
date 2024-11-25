package com.example.roomdatabase.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.data.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(
    private val notesRepository: NotesRepository
): ViewModel() {

    val notes = notesRepository.getNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(id)
        }
    }

}
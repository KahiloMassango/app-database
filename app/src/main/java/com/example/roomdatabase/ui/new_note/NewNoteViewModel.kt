package com.example.roomdatabase.ui.new_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.data.Note
import com.example.roomdatabase.data.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NewNoteViewModel(
    private val notesRepository: NotesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(NewNoteUiState())
    val uiState = _uiState.asStateFlow()

    fun updateTitle(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun updateDescription(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    private fun resetState() {
        _uiState.value = NewNoteUiState()
    }


    fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newNote = Note(
                    title = uiState.value.title,
                    description = uiState.value.description,
                    date = uiState.value.date
                )
                notesRepository.upsertNote(newNote)
                resetState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class NewNoteUiState(
    val title: String = "",
    val description: String = "",
    val date: String = LocalDateTime.now().toLocalDate().toString()
)
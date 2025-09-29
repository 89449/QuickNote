package app.qn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.qn.data.Note
import app.qn.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

// State for the Detail Screen (editing a single note)
data class NoteUiState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val isNewNote: Boolean = true,
    val isValid: Boolean = false
)

class NoteViewModel(private val dao: NoteDao) : ViewModel() {

    // Home Screen State (List of all notes)
    val allNotes: StateFlow<List<Note>> = dao.getAllNotes()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    // Detail Screen State (State for the note being edited/created)
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    // --- CRUD Operations ---

    fun loadNote(noteId: Int) {
        if (noteId > 0) { // Existing note
            viewModelScope.launch {
                val note = dao.getNoteById(noteId)
                note?.let {
                    _uiState.update { state ->
                        state.copy(
                            id = it.id,
                            title = it.title,
                            content = it.content,
                            isNewNote = false
                        )
                    }
                }
            }
        } else { // New note (ID is 0)
            _uiState.value = NoteUiState(isNewNote = true)
        }
    }

    fun updateNoteDetails(title: String, content: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                content = content,
                isValid = title.isNotBlank() || content.isNotBlank()
            )
        }
    }

    fun saveNote() {
        if (!_uiState.value.isValid) return

        val currentState = _uiState.value
        val note = Note(
            id = currentState.id.takeIf { !currentState.isNewNote } ?: 0,
            title = currentState.title.trim(),
            content = currentState.content.trim()
        )
        viewModelScope.launch {
            dao.insert(note)
        }
    }

    fun deleteNote() {
        if (_uiState.value.isNewNote) return

        val noteToDelete = Note(
            id = _uiState.value.id,
            title = _uiState.value.title,
            content = _uiState.value.content
        )
        viewModelScope.launch {
            dao.delete(noteToDelete)
        }
    }
}

// Boilerplate to create the ViewModel with a dependency
class NoteViewModelFactory(private val dao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
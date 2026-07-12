package com.hasancbngl.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasancbngl.notesapp.db.NoteDatabase
import com.hasancbngl.notesapp.model.Note
import kotlinx.coroutines.launch

class HomeViewModel(
    noteDatabase: NoteDatabase
) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    fun addNotes(note: Note) {
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }


}
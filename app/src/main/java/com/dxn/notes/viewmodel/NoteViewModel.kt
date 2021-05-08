package com.dxn.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dxn.notes.models.database.NoteDatabase
import com.dxn.notes.models.entity.Note
import com.dxn.notes.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var allNotes: LiveData<List<Note>>
    private var repository : NoteRepository

    init {
        val dao = NoteDatabase.getNoteDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes

    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

}
package com.dxn.notes.repositories

import androidx.lifecycle.LiveData
import com.dxn.notes.models.dao.NoteDao
import com.dxn.notes.models.entity.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAll()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun deleteById(id:Int) {
        noteDao.deleteById(id)
    }
}
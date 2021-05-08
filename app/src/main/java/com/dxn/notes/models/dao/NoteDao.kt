package com.dxn.notes.models.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dxn.notes.models.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note : Note)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAll() : LiveData<List<Note>>

    @Query("DELETE FROM notes_table WHERE id = :ID")
    suspend fun deleteById(ID : Int)
}
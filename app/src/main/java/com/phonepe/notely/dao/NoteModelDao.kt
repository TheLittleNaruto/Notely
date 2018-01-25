package com.phonepe.notely.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.phonepe.notely.dao.model.Note


/**
 * Created by Kumar Gaurav on 1/23/2018.
 * @Link #NoteModelDao has all db operations methods for storing/deleting/updating a Note
 */
@Dao
interface NoteModelDao {

    @Query("select * from Note")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("select * from Note where id = :id")
    fun getNoteById(id: Long): Note

    @Insert(onConflict = REPLACE)
    fun insertNote(note: Note):Long

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(Note: Note)

}
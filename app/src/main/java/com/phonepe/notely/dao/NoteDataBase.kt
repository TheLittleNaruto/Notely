package com.phonepe.notely.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.phonepe.notely.dao.model.Note

/**
 * Created by Kumar Gaurav on 1/23/2018.
 *
 * Roomdatabase responisble for creating database tables
 *
 */

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteModelDao
}

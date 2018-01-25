package com.phonepe.notely.dao

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by Kumar Gaurav on 1/25/2018.
 *
 * A database access object provider class which will be used to do db operation
 */
class DatabaseProvider {

    companion object {
        fun providesNoteDatabase(context: Context): NoteDataBase =
                Room.databaseBuilder(context, NoteDataBase::class.java, "note_db").build()

        fun providesNoteDao(context: Context) = providesNoteDatabase(context).noteDao()
    }
}
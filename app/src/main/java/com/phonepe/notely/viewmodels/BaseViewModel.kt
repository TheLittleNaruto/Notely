package com.phonepe.notely.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.AsyncTask
import com.phonepe.notely.dao.DatabaseProvider
import com.phonepe.notely.dao.NoteModelDao
import com.phonepe.notely.dao.model.Note

/**
 * Created by Kumar Gaurav on 1/22/2018.
 */
abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    var mNoteModelDao: NoteModelDao = DatabaseProvider.providesNoteDao(application)

    /*update a note
        @param note to be updated
     */
    fun updateItem(note: Note){
        updateAsyncTask(mNoteModelDao).execute(note)
    }

    protected class updateAsyncTask internal constructor(private val db: NoteModelDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            db.updateNote(params[0])
            return null
        }

    }
}
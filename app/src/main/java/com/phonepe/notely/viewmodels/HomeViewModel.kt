package com.phonepe.notely.viewmodels

import android.app.Application
import android.arch.lifecycle.LiveData
import com.phonepe.notely.dao.NoteModelDao
import com.phonepe.notely.dao.model.Note
import android.os.AsyncTask



/**
 * Created by Kumar Gaurav on 1/22/2018.
 */
class HomeViewModel constructor(application: Application): BaseViewModel(application) {



    fun loadNotes(): LiveData<List<Note>> {
        return mNoteModelDao.getAllNotes()
    }

    fun deleteItem(note: Note) {
        deleteAsyncTask(mNoteModelDao).execute(note)
    }

    private class deleteAsyncTask internal constructor(private val db: NoteModelDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            db.deleteNote(params[0])
            return null
        }

    }

    override fun onCleared() {
        super.onCleared()

    }
}